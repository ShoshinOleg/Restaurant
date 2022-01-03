package com.shoshin.data.api.repositories

import com.shoshin.data.api.main.NetworkHelper
import com.shoshin.data.api.main.RestaurantService
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.domain_abstract.repositories.ICategoryRepository

class CategoryRepository(
    private val service: RestaurantService
) : ICategoryRepository {
    override suspend fun getCategories(): Reaction<List<MenuCategory>> {
        return NetworkHelper.safeApiCall { service.getCategories() }
    }

    override suspend fun getCategory(id: String): Reaction<MenuCategory> {
        return NetworkHelper.safeApiCall { service.getCategory(id) }
    }
}