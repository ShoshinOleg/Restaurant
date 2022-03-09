package com.shoshin.domain_abstract.repositories

interface IUserRepository {
    suspend fun registerSignedInUser()
}