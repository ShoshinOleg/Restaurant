package com.shoshin.restaurant.ui.fragments.locations.location_add

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.restaurant.common.argument
import com.shoshin.restaurant.common.argumentNullable
import com.shoshin.restaurant.databinding.LocationAddDialogFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationAddDialogFragment: AppCompatDialogFragment() {
    private lateinit var binding: LocationAddDialogFragmentBinding
    private val viewModel: LocationAddViewModel by viewModels()
    private var location: Location? by argumentNullable()
    private var requestKey: String by argument()

    companion object {
        @JvmStatic private val TAG = LocationAddDialogFragment::class.java.simpleName
        @JvmStatic val REQUEST_KEY_ADD = "$TAG:REQUEST_KEY_ADD"
        @JvmStatic val REQUEST_KEY_EDIT = "$TAG:REQUEST_KEY_EDIT"
        @JvmStatic private val RESPONSE_LOCATION_KEY = "RESPONSE_LOCATION_KEY"

        fun show(manager: FragmentManager, requestKey: String, location: Location? = null) {
            LocationAddDialogFragment().apply {
                this.location = location
                this.requestKey = requestKey
            }.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            requestKey: String,
            listener: (location: Location) -> Unit
        ) {
                manager.setFragmentResultListener(
                    requestKey,
                    lifecycleOwner,
                    { key, bundle ->
                        val location = bundle.get(RESPONSE_LOCATION_KEY) as Location
                        listener.invoke(location)
                    }
                )
        }
    }

    override fun onResume() {
        super.onResume()
        subscribeAddLocation()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = LocationAddDialogFragmentBinding.inflate(layoutInflater)
        binding.closeImage.setOnClickListener {
            dismiss()
        }

        location?.let{
            binding.saveButton.text = "Сохранить"
            binding.streetEditText.setText(it.street)
            binding.houseEditText.setText(it.house)
            binding.entranceEditText.setText(it.entrance)
            binding.intercomCodeEditText.setText(it.intercomCode)
            binding.levelEditText.setText(it.level)
            binding.commentEditText.setText(it.comment)
        }

        binding.saveButton.setOnClickListener {
            val locationResult = Location(
                location?.id,
                street = binding.streetEditText.text.toString(),
                house = binding.houseEditText.text.toString(),
                flat = binding.flatEditText.text.toString(),
                entrance = binding.entranceEditText.text.toString(),
                intercomCode = binding.intercomCodeEditText.text.toString(),
                level = binding.levelEditText.text.toString(),
                comment = binding.commentEditText.text.toString(),
                null
            )
            viewModel.setLocation(locationResult)
        }

        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
    }

    private fun subscribeAddLocation() {
        viewModel.locations.observe(this) {
            when(it) {
                is Reaction.Success -> {
                    parentFragmentManager.setFragmentResult(requestKey, bundleOf(
                        RESPONSE_LOCATION_KEY to it.data
                    ))
                    dismiss()
                }
                is Reaction.Progress -> {
                    Log.e("Progress", "Progress")
                    binding.progress.isVisible = true
                }
                is Reaction.Error -> {
                    Log.e("Error", "Error")
                    binding.progress.isVisible = false
                }
            }
        }
    }
}