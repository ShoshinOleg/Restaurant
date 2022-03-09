package com.shoshin.domain_abstract.repositories

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.domain_abstract.entities.dish.Dish

interface IDishRepository {
    suspend fun getDishByCategory(category: Category) : Reaction<List<Dish>>
}