package com.shoshin.restaurant.ui.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.ProfileFragmentBinding

class ProfileFragment: Fragment(R.layout.profile_fragment) {
    private val binding by viewBinding(ProfileFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.phoneNumber.text = Firebase.auth.currentUser?.phoneNumber

        binding.logoutSection.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(R.id.loginEnterPhone)
        }
    }
}