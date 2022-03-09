package com.shoshin.restaurant.ui.fragments.login

import androidx.lifecycle.*
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.usecases.user.IRegisterSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val registerUserUseCase: IRegisterSignInUseCase
): ViewModel() {
    private val mutableIsSignedIn: MutableLiveData<Reaction<Unit>> = MutableLiveData()
    val isSignedIn = mutableIsSignedIn as LiveData<Reaction<Unit>>

    fun signIn(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            try {
                mutableIsSignedIn.value = Reaction.Progress()
                signInWithCredential(credential)
                registerUserUseCase.execute()
                mutableIsSignedIn.value = Reaction.Success(Unit)
            } catch (e: Exception) {
                mutableIsSignedIn.value = Reaction.Error(message = e.message)
                Firebase.auth.signOut()
            }
        }
    }

    private suspend fun signInWithCredential(credential: PhoneAuthCredential) {
        return suspendCoroutine { continuation ->
            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        continuation.resume(Unit)
                    } else {
                        throw task.exception?.cause ?: Throwable(message = task.exception?.message)
                    }
                }
        }
    }
}