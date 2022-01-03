package com.shoshin.domain_abstract.entities.dish

import java.io.Serializable

data class DishOption (
    var id: String? = null,
    var name: String? = null,
    var variants: HashMap<String, DishOptionVariant>? = hashMapOf(),
    var isMultiCheck: Boolean = false,
    var isNecessary: Boolean = false,
    var isChecked: Boolean? = null
): Serializable {
    fun checkOption() : Boolean{
        if(!isNecessary) {
            isChecked = true
            return true
        } else {
            val keys = variants?.keys?.sorted()!!
            for(key in keys) {
                if(variants!![key]?.isChecked!!) {
                    isChecked = true
                    return true
                }
            }
        }
        isChecked = false
        return false
    }
}