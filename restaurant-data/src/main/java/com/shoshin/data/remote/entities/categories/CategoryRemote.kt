package com.shoshin.data.remote.entities.categories

import java.io.Serializable

data class CategoryRemote (
    var id: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var dishesIds: HashMap<String, String>? = hashMapOf()
): Serializable