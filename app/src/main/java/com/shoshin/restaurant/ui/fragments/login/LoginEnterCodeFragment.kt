package com.shoshin.restaurant.ui.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.argument
import com.shoshin.restaurant.databinding.LoginEnterCodeFragmentBinding
import com.shoshin.restaurant.ui.common.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class LoginEnterCodeFragment: Fragment(R.layout.login_enter_code_fragment) {
    private val binding by viewBinding(LoginEnterCodeFragmentBinding::bind)
    private var verificationId: String by argument()
    private val loginViewModel: LoginViewModel by viewModels()
    private val savedStateHandle by lazy { findNavController().previousBackStackEntry!!.savedStateHandle }

    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Firebase.auth.currentUser != null) {
            savedStateHandle.set(LoginEnterNumberPhoneFragment.LOGIN_SUCCESSFUL, true)
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("onResume", "onResume")
        toEnterMode()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedStateHandle.set(LOGIN_SUCCESSFUL, false)
        toEnterMode()
        Log.e("verId", verificationId)
        binding.nextButton.setOnClickListener { enterCode() }
        binding.mainConstraint.setOnClickListener { activity?.hideKeyboard() }
        subscribeSignedIn()
    }

    private fun subscribeSignedIn() {
        loginViewModel.isSignedIn.observe(requireActivity(), { event ->
            when(event) {
                is Reaction.Progress -> toDownloadMode()
                is Reaction.Success -> {
                    savedStateHandle.set(LOGIN_SUCCESSFUL, true)
                    findNavController().popBackStack()
                }
                is Reaction.Error -> toErrorMode(event.message ?: "Ошибка входа")
            }
        })
    }


    private fun enterCode() {
        Log.i("enterCode", "enterCode")
        val code = binding.codeEditText.text.toString()
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential1(credential)
    }

    private fun signInWithPhoneAuthCredential1(credential: PhoneAuthCredential) =
        loginViewModel.signIn(credential)

    private fun toErrorMode(message: String) {
        binding.error.text = message
        binding.error.visibility = View.VISIBLE
        toEnterMode()
    }

    private fun toDownloadMode() {
        activity?.hideKeyboard()
        binding.mainConstraint.visibility = View.GONE
        binding.progressLayout.visibility = View.VISIBLE
    }

    private fun toEnterMode() {
        binding.progressLayout.visibility = View.GONE
        binding.mainConstraint.visibility = View.VISIBLE
    }
}