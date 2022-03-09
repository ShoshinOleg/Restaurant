package com.shoshin.data.db.entities.categories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryDbo>)

    @Query("UPDATE categories SET downloadTime = :downloadTime WHERE id = :categoryId")
    suspend fun setUpdatedTime(downloadTime: Long, categoryId: String)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()
}