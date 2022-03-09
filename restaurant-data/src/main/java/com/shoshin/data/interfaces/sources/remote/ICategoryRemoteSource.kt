package com.shoshin.data.interfaces.sources.remote

import com.shoshin.data.entities.categories.CategoryData
import com.shoshin.domain_abstract.common.Reaction

interface ICategoryRemoteSource {
    suspend fun getCategories(): Reaction<List<CategoryData>>
}