package com.shoshin.domain.usecases.category

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.domain_abstract.repositories.ICategoryRepository
import com.shoshin.domain_abstract.usecases.category.IGetCategoriesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: ICategoryRepository
): IGetCategoriesUseCase {
    override suspend fun execute(needRemoteDownload: Boolean): Flow<Reaction<List<Category>>> =
        categoryRepository.getCategories(needRemoteDownload)
}