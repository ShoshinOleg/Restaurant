package com.shoshin.data.remote.main

import android.util.Log
import com.google.gson.Gson
import com.shoshin.domain_abstract.common.ErrorResponse
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.common.StatusException
import retrofit2.HttpException

class NetworkHelper {
    companion object {
        suspend fun <T> safeApiCall(apiCall: suspend () -> T) : Reaction<T> {
            return try {
                Reaction.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when(throwable) {
                    is HttpException -> {
//                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        Reaction.Error(exception = errorResponse)
                    }
                    else -> {
                        Log.e("throwable=", "throwable=$throwable")
                        Reaction.Error(null)
                    }
                }
            }
        }

        private fun convertErrorBody(throwable: HttpException): StatusException? {
            return try {
                throwable.response()?.errorBody()?.string()?.let {
                    Gson().getAdapter(StatusException::class.java).fromJson(it)
                }
            } catch (exception: Exception) {
                null
            }
        }
    }
}