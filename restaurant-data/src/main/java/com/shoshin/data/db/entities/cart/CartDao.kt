package com.shoshin.data.db.entities.cart

import androidx.room.*
import com.shoshin.data.db.entities.cart.CartItem1Dbo

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): List<CartItem1Dbo>

    @Insert
    fun insert(cartItemDbo: CartItem1Dbo)

    @Update
    fun update(cartItemDbo: CartItem1Dbo)

    @Delete
    fun remove(cartItemDbo: CartItem1Dbo)

    @Query("DELETE FROM cart")
    fun clear()
}