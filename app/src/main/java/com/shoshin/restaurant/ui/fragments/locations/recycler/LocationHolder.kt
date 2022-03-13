package com.shoshin.restaurant.ui.fragments.locations.recycler

import android.view.View
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.restaurant.databinding.LocationItemHolderBinding
import com.shoshin.restaurant.ui.common.BaseViewHolder

class LocationHolder(
    itemView: View,
    private val editor: (location: Location) -> Unit = {},
    private val deleter: (location: Location) -> Unit = {}
): BaseViewHolder<Location>(itemView) {
    private var binding = LocationItemHolderBinding.bind(itemView)

    init {
        binding.editImage.setOnClickListener { item?.let(editor) }
        binding.removeImage.setOnClickListener { item?.let(deleter) }
    }

    override fun bind(item: Location) {
        super.bind(item)
        binding.name.text = item.fullName()
    }
}