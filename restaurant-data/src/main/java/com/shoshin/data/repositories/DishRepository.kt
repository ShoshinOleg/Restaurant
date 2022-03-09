package com.shoshin.data.repositories

import com.shoshin.data.remote.main.NetworkHelper
import com.shoshin.data.remote.main.RestaurantService
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.repositories.IDishRepository

class DishRepository(
    private val service: RestaurantService
): IDishRepository {
    override suspend fun getDishByCategory(category: Category): Reaction<List<Dish>> {
        return NetworkHelper.safeApiCall { service.getDishesByCategory(category.id!!) }
    }
}