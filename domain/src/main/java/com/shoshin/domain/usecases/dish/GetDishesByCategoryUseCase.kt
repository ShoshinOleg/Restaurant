package com.shoshin.domain.usecases.dish

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.repositories.IDishRepository
import com.shoshin.domain_abstract.usecases.dish.IGetDishesByCategoryUseCase
import javax.inject.Inject

class GetDishesByCategoryUseCase @Inject constructor(
    private val dishRepository: IDishRepository
): IGetDishesByCategoryUseCase {
    override suspend fun execute(category: Category): Reaction<List<Dish>> {
        return dishRepository.getDishByCategory(category)
    }
}