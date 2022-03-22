package com.shoshin.data.db.entities.cart1

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao1 {
    @Query("SELECT * FROM cart1")
    fun getAllFlow(): Flow<List<CartItemDbo1>>

    @Query("SELECT * FROM cart1")
    suspend fun getAll(): List<CartItemDbo1>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItemDbo: CartItemDbo1)

    @Update
    suspend fun update(cartItemDbo: CartItemDbo1)

    @Delete
    suspend fun remove(cartItemDbo: CartItemDbo1)

    @Query("DELETE FROM cart1")
    suspend fun clear()
}