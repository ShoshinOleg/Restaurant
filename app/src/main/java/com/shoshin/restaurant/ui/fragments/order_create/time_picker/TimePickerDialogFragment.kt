package com.shoshin.restaurant.ui.fragments.order_create.time_picker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import com.shoshin.restaurant.common.argument
import com.shoshin.restaurant.databinding.TimePickerFragmentBinding
import com.shoshin.restaurant.ui.fragments.order_create.AvailableTimeRange
import java.util.*

class TimePickerDialogFragment:
    AppCompatDialogFragment(),
    TimeHolder.OnItemCheckListener
{
    private lateinit var binding: TimePickerFragmentBinding
    private val adapter = TimeAdapter(this)

    private var date: Date by argument()
    private var availableTimeRange: AvailableTimeRange by argument()

    companion object {
        @JvmStatic private val TAG = TimePickerDialogFragment::class.java.simpleName
        @JvmStatic private val REQUEST_DEFAULT = "$TAG:REQUEST_DEFAULT"
        @JvmStatic private val RESPONSE_TIME = "$TAG:RESPONSE_TIME"

        fun show(manager: FragmentManager, timeRange: AvailableTimeRange) {
            return TimePickerDialogFragment().apply {
                arguments = bundleOf(
                    "availableTimeRange" to timeRange
                )
            }.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (time: Date?) -> Unit
        ) {
            manager.setFragmentResultListener(
                REQUEST_DEFAULT,
                lifecycleOwner,
                { key, bundle ->
                    val time = bundle.get(RESPONSE_TIME) as Date?
                    listener.invoke(time)
                }
            )
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = TimePickerFragmentBinding.inflate(layoutInflater)
        binding.rvTime.adapter = adapter

        if(availableTimeRange.canDoOrder) {
            onGetAvailableRange(availableTimeRange)
        } else {
            binding.mainInfoConstraint.visibility = View.GONE
            binding.notAvailableTimeConstraint.visibility = View.VISIBLE
        }

        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
    }

    private fun onGetAvailableRange(range: AvailableTimeRange) {
        val datesList = range.getTimeList()
        Log.e("dates", "$datesList")
        if(datesList != null && datesList.isNotEmpty()) {
            adapter.setupTimeList(datesList)
        } else {
            binding.mainInfoConstraint.visibility = View.GONE
            binding.notAvailableTimeConstraint.visibility = View.VISIBLE
        }

    }

    override fun onItemCheck(position: Int) {
        val time = adapter.timeList!![position]
        setFragmentResult(
            REQUEST_DEFAULT,
            bundleOf(
                RESPONSE_TIME to time
            )
        )
        dismiss()
    }
}