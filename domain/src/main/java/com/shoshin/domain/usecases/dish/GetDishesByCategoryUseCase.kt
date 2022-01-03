package com.shoshin.domain.usecases.dish

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.repositories.IDishRepository
import com.shoshin.domain_abstract.usecases.dish.IGetDishesByCategoryUseCase

class GetDishesByCategoryUseCase (
    private val dishRepository: IDishRepository
) : IGetDishesByCategoryUseCase {
    override suspend fun execute(category: MenuCategory): Reaction<List<Dish>> {
        return dishRepository.getDishByCategory(category)
    }
}