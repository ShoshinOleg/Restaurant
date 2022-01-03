package com.shoshin.data.api.main

import android.util.Log
import com.shoshin.domain_abstract.repositories.IUserTokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthOkHttpInterceptor(
    private val userTokenRepository: IUserTokenRepository,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e("in intercept", "in intercept")
        val builder: Request.Builder = chain.request().newBuilder()
        val userToken = runBlocking { userTokenRepository.fetch() }
        Log.e("token", "token = $userToken")
        if(userToken != null) {
            builder.addHeader("Authorization", "Bearer $userToken")
        }
        return chain.proceed(builder.build())
    }
}