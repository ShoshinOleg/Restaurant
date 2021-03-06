package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.entities.dish.DishOption
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.argument
import com.shoshin.restaurant.common.images.interfaces.ImageLoader
import com.shoshin.restaurant.databinding.DishBottomDialogFragmentBinding
import com.shoshin.restaurant.ui.fragments.cart.CartViewModel1
import com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_bottom_fragment.OptionsBottomFragment
import com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_recycler.OptionAdapter
import com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_recycler.OptionHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DishBottomDialogFragment:
    BottomSheetDialogFragment() ,
    OptionHolder.OnOptionClick
{
    private val binding by viewBinding(DishBottomDialogFragmentBinding::bind)
    private var item: Dish by argument()
    private val adapter = OptionAdapter(this)
    private var count = 1
    private val cartViewModel: CartViewModel1 by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    companion object {
        val TAG: String = DishBottomDialogFragment::class.java.simpleName
        fun newInstance(menuItem: Dish): DishBottomDialogFragment {
            return DishBottomDialogFragment().apply {
                item = menuItem
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupOptionResultListener()
    }

    override fun onStart() {
        super.onStart()
        BottomSheetBehavior.from(binding.root.parent as View).state = BottomSheetBehavior.STATE_EXPANDED
        BottomSheetBehavior.from(binding.root.parent as View).skipCollapsed = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.LargeAppBottomSheetDialogTheme)
//        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dish_bottom_dialog_fragment, container, false)
    }

    private fun setupOptionResultListener() {
        OptionsBottomFragment.setupListener(childFragmentManager, this) { isAdd ->
            if (isAdd) {
                onDoOrderCard()
            }
        }
    }

    private fun initView() {
        Log.e("item", "$item")
        item = item.clone()
        item.count = 1


        fillImage()
        binding.name.text = item.name
        binding.countBar.count.text = "$count"
        fillOtherInfo()
        binding.optionsRecycler.adapter = adapter
        item.options?.let {
            val list = mutableListOf<DishOption>()
            for (optionKey in it.keys.sorted()) {
                list.add(it[optionKey]!!)
            }
            adapter.setupItems(list)
        }


        binding.countBar.addCard.setOnClickListener {
            count += 1
            item.count = item.count!! + 1
            binding.countBar.count.text = "$count"
            binding.price.text = context?.getString(R.string.rubles_price, item.getTotalPrice())
        }

        binding.countBar.removeCard.setOnClickListener {
            if(count > 1) {
                count -= 1
                item.count = item.count!! - 1
                binding.countBar.count.text = "$count"
            }
            binding.price.text = context?.getString(R.string.rubles_price, item.getTotalPrice())
        }
        binding.doOrderCard.setOnClickListener {
            onDoOrderCard()
        }
        updatePrice()
    }

    fun updatePrice() {
        binding.price.text = context?.getString(R.string.rubles_price, item.getTotalPrice())
    }

    private fun fillImage() = imageLoader.load(binding.image, item.imageUrl)

    private fun fillOtherInfo() {
        binding.otherInfo.text = context?.getString(R.string.gram_weight, item.weight)
    }

    override fun onOptionClick(option: DishOption) {
        OptionsBottomFragment.show(
            childFragmentManager,
            item.options!!,
            option
        )
    }

    private fun onDoOrderCard() {
        if(checkNecessaryOptions()) {
            cartViewModel.addDish(item)
            Toast.makeText(context?.applicationContext, "?????????? ?????????????????? ?? ??????????????", Toast.LENGTH_SHORT).show()
            dismiss()
        } else {
            adapter.notifyDataSetChanged()
        }
    }

    private fun checkNecessaryOptions(): Boolean {
        val listOptionOk = MutableList(adapter.getItems().size) {false}
        for(index in adapter.getItems().indices) {
            listOptionOk[index] = adapter.getItems()[index].checkOption()
        }
        for(optionOk in listOptionOk) {
            if(!optionOk) {
                return false
            }
        }
        return true
    }

}