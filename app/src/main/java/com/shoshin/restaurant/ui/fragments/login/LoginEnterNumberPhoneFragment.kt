package com.shoshin.restaurant.ui.fragments.login

import android.app.Activity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.LoginEnterNumberPhoneFragmentBinding
import com.shoshin.restaurant.ui.common.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LoginEnterNumberPhoneFragment: Fragment(R.layout.login_enter_number_phone_fragment) {
    private val binding by viewBinding(LoginEnterNumberPhoneFragmentBinding::bind)
    private val callbacks by lazy { CustomVerificationCallbacks(
        loginViewModel::signIn,
        ::onVerificationFailed,
        ::onSendCode
    )}
    private val loginViewModel: LoginViewModel by viewModels()
    private val previousSavedStateHandle by lazy { findNavController().previousBackStackEntry!!.savedStateHandle }

    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Firebase.auth.currentUser != null) {
            previousSavedStateHandle.set(LOGIN_SUCCESSFUL, true)
            findNavController().popBackStack()
        } else {
            val navController = findNavController()
            val currentBackStackEntry = navController.currentBackStackEntry!!
            val savedStateHandle = currentBackStackEntry.savedStateHandle
            savedStateHandle.getLiveData<Boolean>(LoginEnterCodeFragment.LOGIN_SUCCESSFUL)
                .observe(currentBackStackEntry) { success ->
                    if(success) {
                        previousSavedStateHandle.set(LOGIN_SUCCESSFUL, true)
                        findNavController().popBackStack()
                    }
                }
        }
    }

    private fun onSendCode(verificationId: String) {
        val directions = LoginEnterNumberPhoneFragmentDirections.toEnterCode(verificationId)
        findNavController().navigate(directions)
    }

    private fun onVerificationFailed(e: FirebaseException) {
        Log.w("login", "onVerificationFailed", e)
        if (e is FirebaseAuthInvalidCredentialsException) {
            Log.i("ошибка входа", "неверный код")
            showIncorrectPhoneNumber()
            toEnterMode()
        } else if (e is FirebaseTooManyRequestsException) {
            Log.i("many", "закончилась квота")
        }
    }

    private fun subscribeSignedIn() {
        loginViewModel.isSignedIn.observe(requireActivity(), { event ->
            when(event) {
                is Reaction.Progress -> toDownloadMode()
                is Reaction.Success -> {
                    previousSavedStateHandle.set(LOGIN_SUCCESSFUL, true)
                    findNavController().popBackStack()
                }
                is Reaction.Error -> toErrorMode(event.message ?: "Ошибка входа")
            }
        })
    }

    private fun toErrorMode(message: String) {
        binding.error.text = message
        binding.error.visibility = View.VISIBLE
        toEnterMode()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        previousSavedStateHandle.set(LOGIN_SUCCESSFUL, false)
        toEnterMode()
        binding.nextButton.setOnClickListener { onNextButton() }
        binding.mainConstraint.setOnClickListener { activity?.hideKeyboard() }
        binding.phoneEditText.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        subscribeSignedIn()
    }

    private fun showIncorrectPhoneNumber() {
        binding.error.visibility = View.VISIBLE
        binding.error.text = "Неворректный номер телефона"
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

    private fun onNextButton() {
        val phone = binding.phoneEditText.text.toString()
        val resPhone = phone.replace("-","").replace(" ", "")
        if(resPhone.isNotEmpty()) {
            if(resPhone.matches(Regex("^[+]?[0-9]{10,13}\$"))) {
                toDownloadMode()
                authUser(resPhone)
            } else {
                showIncorrectPhoneNumber()
            }
        } else {
            showIncorrectPhoneNumber()
        }
    }

    private fun authUser(phone: String) {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity as Activity)
                .setCallbacks(callbacks)
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}