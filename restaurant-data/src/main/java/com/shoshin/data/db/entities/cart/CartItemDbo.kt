package com.shoshin.data.db.entities.cart

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shoshin.domain_abstract.entities.dish.Dish
import java.io.Serializable

@Entity(tableName = "cart")
data class CartItemDbo (
    @PrimaryKey
    var id: String,
    @Embedded(prefix = "menu_item_")
    var item: Dish
): Serializable
