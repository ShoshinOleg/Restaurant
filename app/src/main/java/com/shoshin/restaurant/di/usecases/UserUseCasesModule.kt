package com.shoshin.restaurant.di.usecases

import com.shoshin.domain.usecases.user.RegisterSignInUseCase
import com.shoshin.domain_abstract.usecases.user.IRegisterSignInUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface UserUseCasesModule {
    @Binds
    fun bindRegisterSignInUseCase(useCase: RegisterSignInUseCase): IRegisterSignInUseCase
}