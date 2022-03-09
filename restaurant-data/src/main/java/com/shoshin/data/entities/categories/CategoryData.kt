package com.shoshin.data.entities.categories

import java.io.Serializable

data class CategoryData (
    var id: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var itemsIds: HashMap<String, String>? = hashMapOf(),
    var downloadTime: Long? = null
): Serializable