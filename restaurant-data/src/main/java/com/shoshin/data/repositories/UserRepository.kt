package com.shoshin.data.repositories

import com.shoshin.data.interfaces.services.remote.IUserRemoteService
import com.shoshin.domain_abstract.repositories.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: IUserRemoteService
): IUserRepository {
    override suspend fun registerSignedInUser() = userService.registerUser()
}