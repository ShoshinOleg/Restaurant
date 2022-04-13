package com.shoshin.restaurant.ui.fragments.order_create.locations_recycler

import android.view.ViewGroup
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.restaurant.R
import com.shoshin.restaurant.ui.common.state_recycler.mutable_state_recycler.BaseMutableStateAdapter

class LocationCheckableAdapter(
    private val editor: (location: Location) -> Unit = {},
    private val deleter: (location: Location) -> Unit = {},
    private val checker: (location: Location?) -> Unit = {}
): BaseMutableStateAdapter<Location, LocationCheckableHolder.CheckedState, LocationCheckableHolder>() {
    var checkedPosition: Int? = null

    override fun same(it1: Location, it2: Location): Boolean = it1.id == it2.id

    override fun getInitialState(): LocationCheckableHolder.CheckedState =
        LocationCheckableHolder.CheckedState(
            isChecked = false,
            bodyState = LocationCheckableHolder.BodyState.Normal
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LocationCheckableHolder(
            R.layout.location_checked_holder.makeView(parent), editor, deleter, ::onItemCheck
        )

    override fun setupItems(
        data: MutableList<Location>,
        initialState: LocationCheckableHolder.CheckedState
    ) {
        super.setupItems(data, initialState)
        checkedPosition = null
        tryCheckFirstItem()
    }

    private fun tryCheckFirstItem() {
        if(items.size > 0) {
            checker(items[0])
            setChecked(0)
        } else {
            checker(null)
            setChecked(null)
        }
    }

    override fun removeItem(item: Location): Int {
        val index = super.removeItem(item)
        if(index == checkedPosition)
            tryCheckFirstItem()
        return index
    }

    private fun onItemCheck(item: Location) {
        val newPosition = items.indexOfFirst { it.id == item.id }
        setChecked(newPosition)
        checker(item)
    }

    private fun setChecked(newPosition: Int?) {
        if(newPosition != null) {
            if(checkedPosition != newPosition) {
                setChecked(newPosition, true)
                checkedPosition?.let { setChecked(checkedPosition!!, false) }
            }
        }
        checkedPosition = newPosition
    }

    private fun setChecked(pos: Int, isChecked: Boolean) {
        states[pos] = states[pos].copy(isChecked = isChecked)
        notifyItemChanged(pos)
    }

    fun setBodyState(location: Location, bodyState: LocationCheckableHolder.BodyState) {
        val index = items.indexOfFirst { location.id == it.id }
        if(index != -1) {
            states[index] = states[index].copy(bodyState = bodyState)
            notifyItemChanged(index)
        }
    }

    fun getCheckedLocation(): Location? {
        return if(checkedPosition != null) items[checkedPosition!!] else null
    }
}