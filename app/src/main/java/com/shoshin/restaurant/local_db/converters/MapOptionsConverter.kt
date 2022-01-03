package com.shoshin.restaurant.local_db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shoshin.domain_abstract.entities.dish.DishOption


class MapConverter {
    @TypeConverter
    fun fromMap(map: HashMap<String, DishOption>?): String {
        return if(map == null) "" else Gson().toJson(map)
    }

    @TypeConverter
    fun toMap(serializedMap: String) : HashMap<String, DishOption> {
        return Gson().fromJson(serializedMap,  object : TypeToken<HashMap<String, DishOption>>() {}.type)
    }


//    @TypeConverter
//    fun fromMap(map: HashMap<String?, String?>?): String {
//        return Gson().toJson(map)
//    }
//
//    @TypeConverter
//    fun fromString(serializedMap: String?): HashMap<String, String> {
//        val type: Type = object : TypeToken<HashMap<String?, String?>?>() {}.type
//        return gson.fromJson("serializedMap", type)
//    }


    //    public HashMap<String, String> fromString(String serializedMap) {
    //        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
    //        return gson.fromJson("serializedMap", type);
    //    }
}