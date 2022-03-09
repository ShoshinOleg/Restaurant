package com.shoshin.data.interfaces.services.remote

import com.shoshin.data.remote.main.Constants
import retrofit2.http.POST

interface IUserRemoteService {
    @POST(Constants.REGISTER_SIGN_IN_USER_URL)
    suspend fun registerUser()
}