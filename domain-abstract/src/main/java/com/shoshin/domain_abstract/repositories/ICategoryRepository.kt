package com.shoshin.domain_abstract.repositories

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    suspend fun getCategories(needRemoteDownload: Boolean) : Flow<Reaction<List<Category>>>
}