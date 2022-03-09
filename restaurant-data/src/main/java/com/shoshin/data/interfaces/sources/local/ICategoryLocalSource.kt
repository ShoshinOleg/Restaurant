package com.shoshin.data.interfaces.sources.local

import com.shoshin.data.entities.categories.CategoryData

interface ICategoryLocalSource {
    suspend fun getAllCategories(): List<CategoryData>
    suspend fun insertAll(categories: List<CategoryData>)
    suspend fun setDownloadTime(downloadTime: Long, categoryId: String)
    suspend fun deleteAll()
}