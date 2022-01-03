package com.shoshin.data.api.main

import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.repositories.IUserTokenRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface RestaurantService {
    @GET(Constants.CATEGORIES_URL)
    suspend fun getCategories(): List<MenuCategory>
    @GET
    suspend fun getCategory(id: String): MenuCategory

    //Dishes
    @GET("${Constants.CATEGORIES_URL}/{category_id}/dishes")
    suspend fun getDishesByCategory(
        @Path(value = "category_id", encoded = true) categoryId: String): List<Dish>

    companion object {
        private var sRestaurantService: RestaurantService? = null
        fun getInstance(userTokenRepository: IUserTokenRepository) : RestaurantService {
            if(sRestaurantService == null) {
                sRestaurantService = getRestaurantService(userTokenRepository)
            }
            return sRestaurantService!!
        }

        private fun getRestaurantService(userTokenRepository: IUserTokenRepository): RestaurantService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(AuthOkHttpInterceptor(userTokenRepository))
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BAS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(RestaurantService::class.java)
        }
    }
}