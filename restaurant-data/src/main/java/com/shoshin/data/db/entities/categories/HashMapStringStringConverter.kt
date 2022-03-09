package com.shoshin.data.db.entities.categories

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HashMapStringStringConverter {
    @TypeConverter
    fun fromMap(map: HashMap<String, String>?): String =
        if(map == null) "" else Gson().toJson(map)

    @TypeConverter
    fun fromMap(serializedMap: String): HashMap<String, String> =
        Gson().fromJson(serializedMap, object : TypeToken<HashMap<String, String>>() {}.type)
}