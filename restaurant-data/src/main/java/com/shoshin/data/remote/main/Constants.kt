package com.shoshin.data.remote.main

import com.shoshin.data.BuildConfig

object Constants {
    const val BASE_URL = BuildConfig.SERVER_URL
    const val CATEGORIES_URL = "categories"
    const val REGISTER_SIGN_IN_USER_URL = "users/register"
    const val LOCATIONS_URL = "locations"
    const val SCHEDULES_URL = "schedules"
    const val ORDERS_URL = "orders"
    const val ORDERS_METADATA_URL = "orders/metadata"
}