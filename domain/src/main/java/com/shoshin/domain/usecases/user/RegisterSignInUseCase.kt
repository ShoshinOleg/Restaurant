package com.shoshin.domain.usecases.user

import com.shoshin.domain_abstract.repositories.IUserRepository
import com.shoshin.domain_abstract.usecases.user.IRegisterSignInUseCase
import javax.inject.Inject

class RegisterSignInUseCase @Inject constructor(
    private val userRepository: IUserRepository
) : IRegisterSignInUseCase {
    override suspend fun execute() = userRepository.registerSignedInUser()
}