package com.shoshin.data.db.entities.categories

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "categories")
class CategoryDbo (
    @PrimaryKey
    var id: String,
    var name: String? = null,
    var imageUrl: String? = null,
    var itemsIds: HashMap<String, String>? = hashMapOf(),
    var downloadTime: Long? = null
): Serializable