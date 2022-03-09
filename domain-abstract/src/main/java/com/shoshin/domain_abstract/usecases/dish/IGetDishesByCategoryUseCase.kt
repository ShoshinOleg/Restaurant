package com.shoshin.domain_abstract.usecases.dish

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.domain_abstract.entities.dish.Dish

interface IGetDishesByCategoryUseCase {
    suspend fun execute(category: Category): Reaction<List<Dish>>
}