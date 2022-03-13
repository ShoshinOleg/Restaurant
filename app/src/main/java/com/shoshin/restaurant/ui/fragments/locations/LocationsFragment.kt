package com.shoshin.restaurant.ui.fragments.locations

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.LocationsFragmentBinding
import com.shoshin.restaurant.ui.fragments.locations.location_add.LocationAddDialogFragment
import com.shoshin.restaurant.ui.fragments.locations.recycler.LocationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment: Fragment(R.layout.locations_fragment) {
    private val binding by viewBinding(LocationsFragmentBinding::bind)
    private lateinit var adapter: LocationAdapter
    private val viewModel: LocationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAddLocationButtonClickListener()
        initRecycler()
        subscribeLocations()
        viewModel.getLocations()
        setupSwipeRefreshLayout()
    }

    private fun setupAddLocationButtonClickListener() {
        binding.addLocationButton.setOnClickListener {
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
                adapter.addItem(it)
            }
    }

    private fun toDataMode() {
        binding.errorLayout.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.mainLayout.isVisible = true
    }

    private fun toErrorMode(errorText: String?) {
        binding.mainLayout.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.errorText.text = errorText
        binding.errorLayout.isVisible = true
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getLocations(true)
        }
    }

    private fun initRecycler() {
        adapter = LocationAdapter(::onEditLocation, ::onDeleteLocation)
        binding.recyclerView.adapter = adapter
    }

    private fun subscribeLocations() {
        viewModel.locations.observe(viewLifecycleOwner, { event ->
            when(event) {
                is Reaction.Success -> showSuccessData(event.data)
                is Reaction.Progress -> onProgressEvent(event)
                is Reaction.Error -> toErrorMode("Ошибка")
            }
        })
    }

    private fun onProgressEvent(event: Reaction.Progress<List<Location>>) {
        event.data?.let { locations->
            adapter.setupItems(locations as MutableList)
            binding.mainLayout.isVisible = true
        }
        binding.swipeRefreshLayout.isRefreshing = true
        binding.errorLayout.isVisible = false
    }

    private fun showSuccessData(locations: List<Location>) {
        Log.e("locs", "locations=$locations")
        adapter.setupItems(locations as MutableList)
        toDataMode()
    }

    private fun onEditLocation(location: Location) {}

    private fun onDeleteLocation(location: Location) {}
}

//class LocationsFragment
//    : Fragment(R.layout.locations_fragment),
//    LocationHolder.OnItemEdit {
//    private val binding by viewBinding(LocationsFragmentBinding::bind)
//    private var adapter: LocationsAdapter? = null
//    private var editPosition: Int? = null
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initRecycler()
//        binding.addLocationButton.setOnClickListener {
//            LocationAddDialogFragment.show(
//                parentFragmentManager,
//                LocationAddDialogFragment.REQUEST_KEY_ADD
//            )
//        }
//
//        setupAddLocationListener()
//        setupEditLocationListener()
//    }
//
//    private fun initRecycler() {
//        adapter = LocationsAdapter(
//            activity?.application as App,
//            this
//        )
//        binding.recyclerView.adapter = adapter
//        LocationRepo.getLocations {
//            adapter?.setupLocations(it)
//        }
//    }
//
//    private fun setupEditLocationListener() {
//        LocationAddDialogFragment
//            .setupListener(
//                parentFragmentManager,
//                this,
//                LocationAddDialogFragment.REQUEST_KEY_EDIT
//            ) {
//                adapter?.locations!![editPosition!!] = it!!
//                adapter?.notifyItemChanged(editPosition!!)
//                editPosition = null
//            }
//    }
//
//    private fun setupAddLocationListener(){
//        LocationAddDialogFragment
//            .setupListener(
//                parentFragmentManager,
//                this,
//                LocationAddDialogFragment.REQUEST_KEY_ADD
//            ) {
//                adapter?.locations!!.add(it!!)
//                adapter?.notifyItemInserted(adapter?.locations!!.size)
//            }
//    }
//
//    override fun onItemEdit(locationId: String) {
//        val position = adapter?.locations?.indexOfFirst { it.id == locationId }
//        if (position != -1) {
//            editPosition = position
//        }
//        LocationAddDialogFragment.show(
//            parentFragmentManager,
//            LocationAddDialogFragment.REQUEST_KEY_EDIT,
//            adapter?.locations!![position!!],
//        )
//    }
//}