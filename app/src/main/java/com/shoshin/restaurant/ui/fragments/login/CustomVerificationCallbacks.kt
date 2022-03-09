package com.shoshin.restaurant.ui.fragments.login

import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class CustomVerificationCallbacks(
    private val onCompleted: (credential: PhoneAuthCredential) -> Unit,
    private val onFailed: (e: FirebaseException) -> Unit,
    private val onSendCode: (verificationId: String) -> Unit

): PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    override fun onVerificationCompleted(credential: PhoneAuthCredential) = onCompleted(credential)

    override fun onVerificationFailed(e: FirebaseException) = onFailed(e)

    override fun onCodeSent(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        super.onCodeSent(verificationId, token)
        onSendCode(verificationId)
    }
}