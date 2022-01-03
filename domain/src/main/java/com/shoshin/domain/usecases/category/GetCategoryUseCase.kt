package com.shoshin.domain.usecases.category

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.domain_abstract.repositories.ICategoryRepository
import com.shoshin.domain_abstract.usecases.category.IGetCategoryUseCase

class GetCategoryUseCase(
    private val categoryRepository: ICategoryRepository
) : IGetCategoryUseCase {
    override suspend fun execute(id: String): Reaction<MenuCategory> {
        return categoryRepository.getCategory(id)
    }
}