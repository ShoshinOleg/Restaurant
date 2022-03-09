package com.shoshin.domain_abstract.usecases.category

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import kotlinx.coroutines.flow.Flow

interface IGetCategoriesUseCase {
    suspend fun execute(needRemoteDownload: Boolean = false) : Flow<Reaction<List<Category>>>
}