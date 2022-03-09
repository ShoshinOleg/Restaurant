package com.shoshin.data.db.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shoshin.data.db.entities.cart.CartItem1Dbo
import com.shoshin.data.db.entities.cart.CartDao
import com.shoshin.data.db.entities.categories.CategoryDao
import com.shoshin.data.db.entities.categories.CategoryDbo
import com.shoshin.data.db.entities.cart.MapOptionsConverter
import com.shoshin.data.db.entities.categories.HashMapStringStringConverter

@Database(
    entities = [
        CartItem1Dbo::class,
        CategoryDbo::class
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
}