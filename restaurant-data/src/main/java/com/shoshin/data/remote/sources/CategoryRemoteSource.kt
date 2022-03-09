package com.shoshin.data.remote.sources

import com.shoshin.data.entities.categories.CategoryData
import com.shoshin.data.interfaces.services.remote.ICategoryRemoteService
import com.shoshin.data.interfaces.sources.remote.ICategoryRemoteSource
import com.shoshin.data.remote.entities.categories.CategoryRemote
import com.shoshin.data.remote.main.NetworkHelper
import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.common.Reaction
import javax.inject.Inject

class CategoryRemoteSource @Inject constructor(
    private val categoryService: ICategoryRemoteService,
    private val categoryDataRemoteMapper: Mapper<CategoryData, CategoryRemote>
): ICategoryRemoteSource {
    override suspend fun getCategories(): Reaction<List<CategoryData>> {
        return NetworkHelper.safeApiCall {
            categoryDataRemoteMapper.mapFrom(
                categoryService.getCategories()
            )
        }
    }
}