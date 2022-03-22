package com.shoshin.data.db.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shoshin.data.db.entities.cart.CartItemDbo
import com.shoshin.data.db.entities.cart.CartDao
import com.shoshin.data.db.entities.categories.CategoryDao
import com.shoshin.data.db.entities.categories.CategoryDbo
import com.shoshin.data.db.entities.cart.MapOptionsConverter
import com.shoshin.data.db.entities.cart1.CartDao1
import com.shoshin.data.db.entities.cart1.CartItemDbo1
import com.shoshin.data.db.entities.categories.HashMapStringStringConverter
import com.shoshin.data.db.entities.locations.LocationDao
import com.shoshin.data.db.entities.locations.LocationDbo

@Database(
    entities = [
        CartItemDbo::class,
        CategoryDbo::class,
        LocationDbo::class,
        CartItemDbo1::class
    ],
    version = 1
)
@TypeConverters(
    MapOptionsConverter::class,
    HashMapStringStringConverter::class
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun cartDao(): CartDao
    abstract fun cartDao1(): CartDao1
    abstract fun locationDbo(): LocationDao
}