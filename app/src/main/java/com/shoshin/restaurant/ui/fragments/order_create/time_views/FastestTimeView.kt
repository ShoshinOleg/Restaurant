package com.shoshin.restaurant.ui.fragments.order_create.time_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.OrderTimeFastestLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class FastestTimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: OrderTimeFastestLayoutBinding

    val delay: Int = 90
    val delayTimeUnit: Int = Calendar.MINUTE

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.order_time_fastest_layout, this, true)
        binding = OrderTimeFastestLayoutBinding.bind(this)
    }

    fun setFirstDate(firstTime: Date?) {
        if(firstTime != null) {
            val format = SimpleDateFormat("HH:mm")
            val cal = Calendar.getInstance().apply { time = firstTime }
            binding.closeCafeConstraint.visibility = GONE
            binding.mainInfoConstraint.visibility = VISIBLE
            binding.fastestTime.text = format.format(cal.time)
        } else {
            binding.mainInfoConstraint.visibility = GONE
            binding.closeCafeConstraint.visibility = VISIBLE
        }
    }
}