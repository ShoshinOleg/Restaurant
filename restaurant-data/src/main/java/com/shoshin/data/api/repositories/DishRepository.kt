package com.shoshin.data.api.repositories

import com.shoshin.data.api.main.NetworkHelper
import com.shoshin.data.api.main.RestaurantService
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.repositories.IDishRepository

class DishRepository(
    private val service: RestaurantService
): IDishRepository {
    override suspend fun getDishByCategory(category: MenuCategory): Reaction<List<Dish>> {
        return NetworkHelper.safeApiCall { service.getDishesByCategory(category.id!!) }
    }
}