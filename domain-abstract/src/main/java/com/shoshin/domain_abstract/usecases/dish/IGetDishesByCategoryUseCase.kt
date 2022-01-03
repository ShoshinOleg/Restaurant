package com.shoshin.domain_abstract.usecases.dish

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.domain_abstract.entities.dish.Dish

interface IGetDishesByCategoryUseCase {
    suspend fun execute(category: MenuCategory): Reaction<List<Dish>>
}