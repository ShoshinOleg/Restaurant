package com.shoshin.restaurant.local_db.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shoshin.domain_abstract.entities.dish.Dish
import java.io.Serializable

@Entity(tableName = "cart")
data class CartItem1Dbo (
    @PrimaryKey
    var id: String,
    @Embedded(prefix = "menu_item_")
    var item: Dish
): Serializable
