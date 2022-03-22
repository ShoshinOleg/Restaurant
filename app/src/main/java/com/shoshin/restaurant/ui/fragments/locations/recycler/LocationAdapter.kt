package com.shoshin.restaurant.ui.fragments.locations.recycler

import android.view.ViewGroup
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.restaurant.R
import com.shoshin.restaurant.ui.common.state_recycler.mutable_state_recycler.BaseMutableStateAdapter

class LocationAdapter(
    private val editor: (location: Location) -> Unit = {},
    private val deleter: (location: Location) -> Unit = {}
): BaseMutableStateAdapter<Location, LocationHolder.LocationState, LocationHolder>() {

    override fun same(it1: Location, it2: Location): Boolean = it1.id == it2.id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder =
        LocationHolder(R.layout.location_item_holder.makeView(parent), editor, deleter)

    override fun getInitialState() = LocationHolder.LocationState.Normal
}