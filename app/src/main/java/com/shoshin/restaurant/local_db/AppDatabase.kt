package com.shoshin.restaurant.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shoshin.restaurant.local_db.converters.MapConverter
import com.shoshin.restaurant.local_db.dao.CartDao
import com.shoshin.restaurant.local_db.models.CartItem1Dbo

@Database(entities = [CartItem1Dbo::class], version = 1)
@TypeConverters(MapConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
}