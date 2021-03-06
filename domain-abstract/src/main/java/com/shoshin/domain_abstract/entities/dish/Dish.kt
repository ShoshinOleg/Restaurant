package com.shoshin.domain_abstract.entities.dish

import com.google.gson.Gson
import java.io.Serializable

data class Dish (
    var id: String? = null,
    var categoryId: String? = null,
    var imageUrl: String? = null,
    var name: String? = null,
    var price: Int? = null,
    var weight: Int? = null,
    var count: Int? = 1,
    var comment: String? = null,
    var options: HashMap<String, DishOption>? = hashMapOf()
): Serializable {

    fun clone(): Dish {
        val string = Gson().toJson(this, Dish::class.java)
        return Gson().fromJson<Dish>(string, Dish::class.java)
    }

    fun isEqual(item: Dish): Boolean {
        if(id != item.id) {
            return false
        } else {
            for(optionKey in options?.keys?.sorted()!!) {
                val variantKeys = options!![optionKey]?.variants?.keys!!
                for(variantKey in variantKeys) {
                    if(options!![optionKey]!!.variants!![variantKey]?.isChecked!!
                        != item.options!![optionKey]?.variants!![variantKey]?.isChecked!!) {
                        return false
                    }
                }
            }
            return true
        }
    }

    fun getTotalPrice(): Int {
        if(count != null && price != null) {
            return count!!*(price!! + sumOptionsPrice())
        } else {
            return 0
        }
    }

    fun sumOptionsPrice(): Int {
        var sum = 0
        options?.let {
            for(option in it.values)  {
                option.variants?.let { variants ->
                    for(variant in variants.values) {
                        variant.price?.let { price ->
                            sum += price
                        }
                    }
                }
            }
        }
        return sum
    }

    fun getSelectedVariantsList() : List<String> {
        val names = mutableListOf<String>()
        options?.keys?.sorted()?.let { optionKeys->
            for(optionKey in optionKeys) {
                val option = options!![optionKey]!!
                option.variants?.keys?.sorted()?.let { variantKeys ->
                    for(variantKey in variantKeys) {
                        val variant = option.variants!![variantKey]!!
                        if(variant.isChecked && variant.name != null) {
                            names.add(variant.name!!)
                        }
                    }
                }
            }
        }
        return names
    }

    fun getOptionsString() : String? {
        val names = getSelectedVariantsList()
        if(names.isNotEmpty()) {
            val builder = StringBuilder("")
            for(name in names) {
                builder.append(name).append(" ?? ")
            }
            var string = builder.toString()
            string = string.substring(0..string.length-4)
            return string
        } else {
            return null
        }
    }
}