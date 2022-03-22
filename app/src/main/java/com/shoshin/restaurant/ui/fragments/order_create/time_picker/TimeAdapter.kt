package com.shoshin.restaurant.ui.fragments.order_create.time_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.restaurant.R
import java.util.*

class TimeAdapter(
    private val onItemCheckListener: TimeHolder.OnItemCheckListener
)
    : RecyclerView.Adapter<TimeHolder>() {
    var timeList: List<Date>? = null

    fun setupTimeList(times: List<Date>?) {
        timeList = times
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_holder, parent, false)
        return TimeHolder(view, onItemCheckListener)
    }

    override fun onBindViewHolder(holder: TimeHolder, position: Int) {
        holder.bind(timeList!![position], position)
    }

    override fun getItemCount(): Int {
        return timeList?.size ?: 0
    }
}