package com.shoshin.domain_abstract.repositories

interface IUserTokenRepository {
    suspend fun fetch(): String?
}