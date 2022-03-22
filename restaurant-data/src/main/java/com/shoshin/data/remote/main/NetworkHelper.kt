package com.shoshin.data.remote.main

import android.util.Log
import com.google.gson.Gson
import com.shoshin.domain_abstract.common.ErrorInfo
import com.shoshin.domain_abstract.common.Reaction
import retrofit2.HttpException

class NetworkHelper {
    companion object {
        suspend fun <T> safeApiCall(apiCall: suspend () -> T) : Reaction<T> {
            return try {
                Reaction.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when(throwable) {
                    is HttpException -> {
                        val errorInfo = convertErrorBody(throwable)
                        Reaction.Error(errorInfo = errorInfo)
                    }
                    else -> {
                        Log.e("errorBody", "${throwable.message}")
                        Log.e("throwable=", "throwable=$throwable")
                        Reaction.Error(null)
                    }
                }
            }
        }

        private fun convertErrorBody(throwable: HttpException): ErrorInfo? {
            return try {
                throwable.response()?.errorBody()?.string()?.let {
                    Gson().getAdapter(ErrorInfo::class.java).fromJson(it)
                }
            } catch (exception: Exception) {
                null
            }
        }
    }
}