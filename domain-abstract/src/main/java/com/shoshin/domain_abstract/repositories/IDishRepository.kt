package com.shoshin.domain_abstract.repositories

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.domain_abstract.entities.dish.Dish
import java.io.File

interface IDishRepository {
    suspend fun getDishByCategory(category: MenuCategory) : Reaction<List<Dish>>
}