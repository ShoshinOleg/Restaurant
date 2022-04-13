package com.shoshin.restaurant.ui.fragments.order_create.time_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.TimeChooseLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class TimeSelectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr, defStyleRes){
    private val binding: TimeChooseLayoutBinding

    val delay: Int = 90
    val delayTimeUnit: Int = Calendar.MINUTE

    private val calendar: Calendar = Calendar.getInstance()
    var isDateSet: Boolean = false
        private set
    var isTimeSet: Boolean = false
        private set

    var timeCardClickListener: () -> Unit = {}
    var dateCardClickListener: () -> Unit = {}

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.time_choose_layout, this, true)
        binding = TimeChooseLayoutBinding.bind(this)
        binding.timeCard.setOnClickListener { timeCardClickListener() }
        binding.dateCard.setOnClickListener { dateCardClickListener() }
    }

    fun getSelectedDateTime(): Date = calendar.time

    fun setFirstDateTime(firstTime: Date?) {
        if(firstTime != null) {
            setDate(firstTime)
            setTime(firstTime)
        }
    }

    fun setDate(date: Date?) {
        if(date != null) {
            val tempCalendar = Calendar.getInstance().apply { this.time = date }
            calendar.set(Calendar.YEAR, tempCalendar.get(Calendar.YEAR))
            calendar.set(Calendar.MONTH, tempCalendar.get(Calendar.MONTH))
            calendar.set(Calendar.DAY_OF_MONTH, tempCalendar.get(Calendar.DAY_OF_MONTH))
            val formatDate = SimpleDateFormat("dd.MM.yy")
            binding.tvDate.text = formatDate.format(calendar.time)
            isDateSet = true
        } else {
            isDateSet = false
        }
    }

    fun setTime(time: Date?) {
        if(time != null) {
            val tempCalendar = Calendar.getInstance().apply { this.time = time }
            calendar.set(Calendar.HOUR_OF_DAY, tempCalendar.get(Calendar.HOUR_OF_DAY))
            calendar.set(Calendar.MINUTE, tempCalendar.get(Calendar.MINUTE))
            val formatTime = SimpleDateFormat("HH:mm")
            binding.tvTime.text = formatTime.format(calendar.time)
            isTimeSet = true
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            binding.tvTime.text = "Время"
            binding.tvTime.setTextColor(ContextCompat.getColor(context, R.color.red))
            isTimeSet = false
        }
    }
}