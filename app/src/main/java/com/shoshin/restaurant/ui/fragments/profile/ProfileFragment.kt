package com.shoshin.restaurant.ui.fragments.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.ProfileFragmentBinding
import com.shoshin.restaurant.ui.fragments.login.LoginEnterNumberPhoneFragment

class ProfileFragment: Fragment(R.layout.profile_fragment) {
    private val binding by viewBinding(ProfileFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController()

        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginEnterNumberPhoneFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) { success ->
                Log.e("orders", "success=$success")
                if(!success) {
                    val startDestination = navController.graph.startDestinationId
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, true)
                        .build()
                    navController.navigate(startDestination, null, navOptions)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(Firebase.auth.currentUser == null) {
            findNavController().navigate(R.id.loginEnterPhone)
        } else {
            binding.phoneNumber.text = Firebase.auth.currentUser?.phoneNumber

            binding.logoutSection.setOnClickListener {
                Firebase.auth.signOut()
                findNavController().navigate(R.id.loginEnterPhone)
            }

            binding.locationsSection.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.toLocations())
            }
        }
    }
}