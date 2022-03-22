package com.shoshin.restaurant.ui.fragments.order_create.time_picker

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.restaurant.databinding.TimeHolderBinding
import java.text.SimpleDateFormat
import java.util.*

class TimeHolder(
    itemView: View,
    private val onItemCheckListener: OnItemCheckListener
): RecyclerView.ViewHolder(itemView) {
    private var binding = TimeHolderBinding.bind(itemView)
    private var time: Date? = null
    private var position: Int? = null

    interface OnItemCheckListener {
        fun onItemCheck(position: Int)
    }

    init {
        binding.rbCheck.setOnClickListener {
            onItemCheckListener.onItemCheck(position!!)
        }
        binding.tvTime.setOnClickListener {
            onItemCheckListener.onItemCheck(position!!)
        }
    }

    fun bind(timeItem: Date, positionInList: Int) {
        time = timeItem
        position = positionInList
        val format = SimpleDateFormat("HH:mm")
        binding.tvTime.text = format.format(time!!)
    }
}