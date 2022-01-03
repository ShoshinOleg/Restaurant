package com.shoshin.restaurant.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoshin.domain_abstract.repositories.IUserTokenRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserTokenRepository: IUserTokenRepository {
    override suspend fun fetch(): String? {
        return suspendCoroutine { continuation ->
            Firebase.auth.currentUser?: continuation.resume(null)
            Firebase.auth.currentUser?.getIdToken(false)
                    ?.addOnCompleteListener { task ->
                        if(task.isSuccessful && task.result != null) {
                            continuation.resume(task.result?.token)
                        } else {
                            continuation.resume(null)
                        }
                    }
        }
    }
}