package com.shoshin.data.db.entities.cart

import androidx.room.*

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): List<CartItemDbo>

    @Insert
    fun insert(cartItemDbo: CartItemDbo)

    @Update
    fun update(cartItemDbo: CartItemDbo)

    @Delete
    fun remove(cartItemDbo: CartItemDbo)

    @Query("DELETE FROM cart")
    fun clear()
}