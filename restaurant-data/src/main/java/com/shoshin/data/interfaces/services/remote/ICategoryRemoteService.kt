package com.shoshin.data.interfaces.services.remote

import com.shoshin.data.remote.entities.categories.CategoryRemote
import com.shoshin.data.remote.main.Constants
import com.shoshin.domain_abstract.entities.category.Category
import retrofit2.http.GET
import retrofit2.http.Path

interface ICategoryRemoteService {
    @GET(Constants.CATEGORIES_URL)
    suspend fun getCategories(): List<CategoryRemote>

    @GET("${Constants.CATEGORIES_URL}/{id}")
    suspend fun getCategory(
        @Path(value = "id", encoded = true) id: String
    ): Category
}