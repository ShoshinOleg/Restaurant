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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
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

    override suspend fun getCategories(needRemoteDownload: Boolean): Flow<Reaction<List<Category>>> =
        flow {
            when {
                needRemoteDownload -> fetchRemote()
                isDataOutdated() -> fetchRemote()
                else -> fetchLocal()
            }
        }

    private suspend fun FlowCollector<Reaction<List<Category>>>.fetchRemote() {
        Log.e("tut0", "tut0")
        if(isDataCached()) {
            Log.e("tut1", "tut1")
            val cats = localCategorySource.getAllCategories()
            Log.e("cats=", "cats=$cats")
            Log.e("tut1c", "tut1c")

            val localCategories = categoryDomainDataMapper.mapFrom(cats)
            Log.e("tut2", "tut2")
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
            is Reaction.Error -> emit(Reaction.Error(result.message, result.exception))
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
//    override suspend fun getCategories(): Reaction<List<MenuCategory>> {
//        return NetworkHelper.safeApiCall { service.getCategories() }
//    }

//    override suspend fun getCategory(id: String): Reaction<MenuCategory> {
//        return NetworkHelper.safeApiCall { service.getCategory(id) }
//    }
}