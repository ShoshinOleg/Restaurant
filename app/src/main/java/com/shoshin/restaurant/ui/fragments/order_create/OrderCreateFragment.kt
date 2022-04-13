package com.shoshin.restaurant.ui.fragments.order_create

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ServiceWorkerController
import android.webkit.WebView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.schedule.WeekSchedule
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.OrderCreateFragmentBinding
import com.shoshin.restaurant.ui.fragments.cart.CartViewModel1
import com.shoshin.restaurant.ui.fragments.locations.LocationsViewModel
import com.shoshin.restaurant.ui.fragments.locations.location_add.LocationAddDialogFragment
import com.shoshin.restaurant.ui.fragments.locations.recycler.LocationHolder
import com.shoshin.restaurant.ui.fragments.login.LoginEnterNumberPhoneFragment
import com.shoshin.restaurant.ui.fragments.order_create.locations_recycler.LocationCheckableAdapter
import com.shoshin.restaurant.ui.fragments.order_create.locations_recycler.LocationCheckableHolder
import com.shoshin.restaurant.ui.fragments.order_create.time_picker.TimePickerDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class OrderCreateFragment: Fragment(R.layout.order_create_fragment) {
    private val binding by viewBinding(OrderCreateFragmentBinding::bind)
    private val locationsViewModel: LocationsViewModel by viewModels()
    private val locationsAdapter by lazy {
        LocationCheckableAdapter(::onEditLocation, ::onRemoveLocation, ::onCheckLocation)
    }
    private val cartViewModel: CartViewModel1 by viewModels()
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private val orderViewModel: OrderCreateViewModel by viewModels()

    private var order = Order()
    private var availableTimeRange: AvailableTimeRange? = null
    private var firstDateTime: Date? = null
    private var weekSchedule: WeekSchedule? = null

    companion object {
        const val DELAY = 90
        const val DELAY_TIME_UNIT = Calendar.MINUTE
        const val DEFAULT_MINIMUM_ORDER_PRICE_FREE_DELIVERY = 600
        const val DEFAULT_DELIVERY_PRICE = 100
        const val DEFAULT_PICKUP_ADDRESS = "пгт. Долгое, ул. Орджоникидзе, д. 2"
        val DEFAULT_PICKUP_LOCATION = Location(
            street = "Орджоникидзе",
            house = "2"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("orderCreate", "orderCreate")
        super.onCreate(savedInstanceState)

        val navController = findNavController()

        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginEnterNumberPhoneFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) { success ->
                Log.e("orders", "success=$success")
                if(!success) {
                    val startDestination = navController.graph.startDestinationId
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, true)
                        .build()
                    navController.navigate(startDestination, null, navOptions)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(Firebase.auth.currentUser == null) {
            Log.e("t1", "t1")
            findNavController().navigate(R.id.loginEnterPhone)
        } else {
            Log.e("t2", "t2")
            initLocations()
            initPaymentMethodBlock()
            initOrderInfo()
            subscribeSchedule()
            observeUpdatedOrder()
            initCreateOrderButton()
            cartViewModel.getItems()
            scheduleViewModel.getDefaultSchedule()
            setupAddLocationButtonClickListener()
            subscribeRemovedLocation()
        }
    }

    private fun setupAddLocationButtonClickListener() {
        binding.orderLocationCheckView.setOnAddClickListener {
            LocationAddDialogFragment.show(
                parentFragmentManager,
                LocationAddDialogFragment.REQUEST_KEY_ADD
            )
        }
        setupAddLocationListener()
    }

    private fun setupAddLocationListener(){
        LocationAddDialogFragment
            .setupListener(
                parentFragmentManager,
                this,
                LocationAddDialogFragment.REQUEST_KEY_ADD,
            ) {
                Log.e("item", "item=$it")
//                binding.orderLocationCheckView?.setState()
                binding.orderLocationCheckView.adapter?.setItem(it)
            }
    }

    private fun initDateTimeBlock(weekSchedule: WeekSchedule) {
        this.weekSchedule = weekSchedule
        availableTimeRange = AvailableTimeRange.getAvailableTimeRange(
            weekSchedule, Calendar.getInstance().time, DELAY, DELAY_TIME_UNIT
        )
        firstDateTime = availableTimeRange?.firstAvailableDate()
        binding.timeSelect.setFirstDateTime(firstDateTime)
        binding.fastestTime.setFirstDate(firstDateTime)
        binding.timeSwitcher.firstClickListener = {
            order.isFastest = true
        }
        binding.timeSwitcher.secondClickListener = {
            order.isFastest = false
        }
        initTimeCard()
        initDateCard()
    }

    private fun initTimeCard() {
        setupTimeSelectListener()
        binding.timeSelect.timeCardClickListener = {
            Log.e("onClick","onClick")
            availableTimeRange?.let { range ->
                TimePickerDialogFragment.show(
                    parentFragmentManager,
                    range
                )
            }
        }
    }

    private fun subscribeRemovedLocation() {
        lifecycleScope.launch {
            locationsViewModel.removedLocation.collect { event ->
                when(event) {
                    is Reaction.Success -> binding.orderLocationCheckView.adapter?.removeItem(event.data)
                    is Reaction.Progress -> event.data?.let { location ->
                        binding.orderLocationCheckView.adapter?.setBodyState(
                            location,
                            LocationCheckableHolder.BodyState.Progress
                        )
                    }
                    is Reaction.Error -> event.data?.let { location ->
                        binding.orderLocationCheckView.adapter?.setBodyState(
                            location,
                            LocationCheckableHolder.BodyState.Error
                        )
                    }
                }
            }
        }
    }

    private fun initDateCard() {
        binding.timeSelect.dateCardClickListener = {
            val selectedDateCalendar = Calendar.getInstance().apply {
                time = binding.timeSelect.getSelectedDateTime()
            }
            DatePickerDialog(
                requireContext(),
                R.style.MyDatePickerStyle,
                { view, year, month, dayOfMonth ->
                    val newDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }.time
                    val lastDateTime = binding.timeSelect.getSelectedDateTime()
                    if(!isEqualDate(lastDateTime, newDate)) {
                        binding.timeSelect.setDate(newDate)
                        binding.timeSelect.setTime(null)
                        weekSchedule?.let { schedule ->
                            availableTimeRange = AvailableTimeRange.getAvailableTimeRange(
                                schedule, binding.timeSelect.getSelectedDateTime(), DELAY, DELAY_TIME_UNIT
                            )
                        }
                    }
                },
                selectedDateCalendar.get(Calendar.YEAR),
                selectedDateCalendar.get(Calendar.MONTH),
                selectedDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.minDate = System.currentTimeMillis()
            }.show()
        }
    }

    private fun isEqualDate(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance().apply { time = date1 }
        val calendar2 = Calendar.getInstance().apply { time = date2 }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }

    private fun setupTimeSelectListener() {
        TimePickerDialogFragment.setupListener(
            parentFragmentManager,
            this,
            { date -> binding.timeSelect.setTime(date) }
        )
    }

    private fun initLocations() {
        binding.orderLocationCheckView.adapter = locationsAdapter
        locationsViewModel.locations.observe(viewLifecycleOwner) { event ->
            binding.orderLocationCheckView.setState(event)
        }
        locationsViewModel.getLocations()
        binding.deliverySwitcher.firstClickListener = {
            binding.totalOrderLayout.deliveryPrice.visibility = View.VISIBLE
            order.isDelivery = true
            order.location = locationsAdapter.getCheckedLocation()
            binding.totalOrderLayout.address.text =
                binding.orderLocationCheckView.adapter?.getCheckedLocation()?.fullName()
            updateDelivery()
        }
        binding.deliverySwitcher.secondClickListener = {
            order.isDelivery = false
            order.location = DEFAULT_PICKUP_LOCATION
            binding.totalOrderLayout.deliveryPrice.visibility = View.GONE
            binding.totalOrderLayout.address.text = DEFAULT_PICKUP_ADDRESS
            updateDelivery()
        }
    }

    private fun initOrderInfo() {
        cartViewModel.cartItems.observe(viewLifecycleOwner, { items ->
            Log.e("items", "items=$items")
            order.items = items.map { it.dish } as MutableList
            Log.e("order.items", "order.items=${order.items}")
        })
        cartViewModel.cartPrice.observe(this, { price ->
            order.orderPrice = price
            updateDelivery()
            binding.totalPrice.text =
                context?.getString(R.string.rubles_price, order.getTotalPrice())
            binding.totalOrderLayout.totalPrice.text =
                context?.getString(R.string.rubles_price, order.getTotalPrice())
            binding.totalOrderLayout.totalOrderPrice.text =
                context?.getString(R.string.rubles_price, order.orderPrice)
            Log.e("cartPrice", "cartPrice=$price")
        })
        order.isDelivery = true
        order.customerId = Firebase.auth.currentUser?.uid
        order.phoneNumber = Firebase.auth.currentUser?.phoneNumber
    }

    private fun updateDelivery() {
        if(binding.deliverySwitcher.selectedTab == 0
            && order.orderPrice!! < DEFAULT_MINIMUM_ORDER_PRICE_FREE_DELIVERY) {
            order.deliveryPrice = DEFAULT_DELIVERY_PRICE
        } else {
            order.deliveryPrice = 0
        }
        binding.totalOrderLayout.deliveryPrice.text =
            context?.getString(R.string.rubles_price, order.deliveryPrice)
    }

    private fun initPaymentMethodBlock() {
        binding.cashPayment.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                binding.totalOrderLayout.paymentMethod.text = buttonView.text
                order.paymentMethod = buttonView.text.toString()
            }
        }
        binding.receiptCardPayment.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                binding.totalOrderLayout.paymentMethod.text = buttonView.text
                order.paymentMethod = buttonView.text.toString()
            }
        }
        binding.cashPayment.isChecked = true
    }

    private fun subscribeSchedule() {
        scheduleViewModel.defaultSchedule.observe(viewLifecycleOwner) { event ->
            when(event) {
                is Reaction.Success -> initDateTimeBlock(event.data)
            }
        }
    }

    private fun onEditLocation(location: Location) {
        LocationAddDialogFragment.show(
            parentFragmentManager,
            LocationAddDialogFragment.REQUEST_KEY_ADD,
            location
        )
    }

    private fun onRemoveLocation(location: Location) = locationsViewModel.removeLocation(location)

    private fun onCheckLocation(location: Location?) {
        if(binding.deliverySwitcher.selectedTab == 0) {
            binding.totalOrderLayout.address.text = location?.fullName()
        }
    }

    private fun initCreateOrderButton() {
        binding.createOrder.setOnClickListener {
            buildOrder()
            Log.e("order", "order=$order")
            if(checkOrder()) {
                orderViewModel.updateOrder(order)
            }
        }
    }

    private fun observeUpdatedOrder() {
        orderViewModel.updatedOrderMetadata.observe(viewLifecycleOwner) { event ->
            when(event) {
                is Reaction.Progress -> binding.creationProgress.visibility = View.VISIBLE
                is Reaction.Success -> {
                    cartViewModel.clearCart()
//                    orderViewModel.
                    findNavController().popBackStack()
                }
                is Reaction.Error -> {}//сделать обработку ошибки
            }
        }
    }

    private fun buildOrder() {
        if(order.isDelivery) {
            order.location = binding.orderLocationCheckView.adapter?.getCheckedLocation()
        } else {
            order.location = DEFAULT_PICKUP_LOCATION
        }
        if(order.location == null) {
            Toast.makeText(context, "Не выбрано место доставки", Toast.LENGTH_SHORT).show()
        }
        val dateTime: Date? = getOrderDate()
        if(dateTime != null) {
            val calendar = Calendar.getInstance().apply { time = dateTime}
            val formatDateTime = SimpleDateFormat("dd:MM:yyyy HH:mm")
            order.orderTime = formatDateTime.format(calendar.time)
            order.createdAt = formatDateTime.format(Calendar.getInstance().time)
            val formatDate = SimpleDateFormat("dd:MM:yyyy")
            order.orderDate = formatDate.format(calendar.time)
        } else {
            Toast.makeText(context, "Не выбрано время доставки", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkOrder(): Boolean = order.location != null && order.orderDate != null

    private fun getOrderDate(): Date? {
        return if(order.isFastest) {
            firstDateTime
        } else {
            if(binding.timeSelect.isDateSet && binding.timeSelect.isTimeSet) {
                binding.timeSelect.getSelectedDateTime()
            } else
                null
        }
    }
}