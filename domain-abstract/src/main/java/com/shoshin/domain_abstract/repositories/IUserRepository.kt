package com.shoshin.domain_abstract.repositories

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.user.RestaurantUser

interface IUserRepository {
    suspend fun updateUser(user: RestaurantUser): Reaction<*>
    suspend fun registerSignedInUser(): Reaction<*>
    suspend fun hasRole(role: String) : Reaction<Boolean>
}