package com.shoshin.data.repositories

import android.util.Log
import com.shoshin.data.entities.categories.CategoryData
import com.shoshin.data.interfaces.sources.local.ICategoryLocalSource
import com.shoshin.data.interfaces.sources.remote.ICategoryRemoteSource
import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.domain_abstract.repositories.ICategoryRepository
import com.shoshin.domain_abstract.repositories.IPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val localCategorySource: ICategoryLocalSource,
    private val remoteCategorySource: ICategoryRemoteSource,
    private val categoryDomainDataMapper: Mapper<Category, CategoryData>,
    private val preferences: IPreferencesRepository
) : ICategoryRepository {

    companion object {
        const val PREFS_KEY_CATEGORIES_UPDATED_TIME = "PREFS_KEY_CATEGORIES_UPDATED_TIME"
    }

    override suspend fun getCategories(needRemoteDownload: Boolean): Flow<Reaction<List<Category>>> = withContext(Dispatchers.IO) {
        flow {
            when {
                needRemoteDownload -> fetchRemote()
                isDataOutdated() -> fetchRemote()
                else -> fetchLocal()
            }
        }
    }

    private suspend fun FlowCollector<Reaction<List<Category>>>.fetchRemote() {
        if(isDataCached()) {
            val cats = localCategorySource.getAllCategories()
            val localCategories = categoryDomainDataMapper.mapFrom(cats)
            emit(Reaction.Progress(localCategories))
        } else {
            emit(Reaction.Progress())
        }

        when(val result = remoteCategorySource.getCategories()) {
            is Reaction.Success -> {
                Log.e("cats", "categories=${result.data}")
                localCategorySource.deleteAll()
                localCategorySource.insertAll(result.data)
                preferences.setLongField(PREFS_KEY_CATEGORIES_UPDATED_TIME, System.currentTimeMillis())
                val remoteCategories = categoryDomainDataMapper.mapFrom(result.data)
                emit(Reaction.Success(remoteCategories))
            }
            is Reaction.Error -> emit(Reaction.Error(result.message, result.errorInfo))
            else -> {}
        }
    }

    private suspend fun FlowCollector<Reaction<List<Category>>>.fetchLocal() {
        Log.e("inFetchLocal", "inFetchLocal")
        val categories = categoryDomainDataMapper.mapFrom(localCategorySource.getAllCategories())
        Log.e("categories", "categories=$categories")
        emit(Reaction.Success(categories))
    }

    private fun isDataCached(): Boolean =
        preferences.getLongField(PREFS_KEY_CATEGORIES_UPDATED_TIME, -1) != -1L

    private fun isDataOutdated(): Boolean {
        val lastDownloaded = preferences.getLongField(PREFS_KEY_CATEGORIES_UPDATED_TIME, -1)
        if(lastDownloaded == -1L) {
            return true
        } else {
            val currentTime = System.currentTimeMillis()
            val diffInMs = (currentTime - lastDownloaded)
            val diff: Long = TimeUnit.MILLISECONDS.toMinutes(diffInMs)
            Log.e("diff", "diff=$diff")
            return diff > 0
        }
    }
}