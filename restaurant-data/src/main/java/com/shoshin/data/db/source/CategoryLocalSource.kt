package com.shoshin.data.db.source

import android.util.Log
import com.shoshin.data.db.entities.categories.CategoryDao
import com.shoshin.data.db.entities.categories.CategoryDbo
import com.shoshin.data.entities.categories.CategoryData
import com.shoshin.data.interfaces.sources.local.ICategoryLocalSource
import com.shoshin.domain_abstract.common.Mapper
import javax.inject.Inject

class CategoryLocalSource @Inject constructor(
    private val categoryDao: CategoryDao,
    private val mapper: Mapper<CategoryData, CategoryDbo>
): ICategoryLocalSource {
    override suspend fun getAllCategories(): List<CategoryData> {
        val categoryDboList = categoryDao.getAllCategories()
        Log.e("categoryDboList", "categoryDboList=$categoryDboList")
        return mapper.mapFrom(categoryDboList)
    }

    override suspend fun insertAll(categories: List<CategoryData>) {
        val categoryDboList = mapper.mapTo(categories)
        categoryDao.insertAll(categoryDboList)
    }

    override suspend fun setDownloadTime(downloadTime: Long, categoryId: String) =
        categoryDao.setUpdatedTime(downloadTime, categoryId)

    override suspend fun deleteAll() = categoryDao.deleteAll()
}