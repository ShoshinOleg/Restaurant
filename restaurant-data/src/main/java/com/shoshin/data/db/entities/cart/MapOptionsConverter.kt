package com.shoshin.data.db.entities.cart

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shoshin.domain_abstract.entities.dish.DishOption

class MapOptionsConverter {
    @TypeConverter
    fun fromMap(map: HashMap<String, DishOption>?): String =
        if(map == null) "" else Gson().toJson(map)

    @TypeConverter
    fun toMap(serializedMap: String) : HashMap<String, DishOption> =
        Gson().fromJson(serializedMap,  object : TypeToken<HashMap<String, DishOption>>() {}.type)
}