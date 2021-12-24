package com.shoshin.domain_abstract.entities.category

import java.io.Serializable

data class MenuCategory (
    var id: String? = null,
    var name: String? = null,
    var imageURL: String? = null,
    var itemsIds: HashMap<String, String>? = null
): Serializable