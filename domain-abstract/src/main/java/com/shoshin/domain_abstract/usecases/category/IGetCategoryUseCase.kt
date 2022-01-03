package com.shoshin.domain_abstract.usecases.category

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory

interface IGetCategoryUseCase {
    suspend fun execute(id: String) : Reaction<MenuCategory>
}