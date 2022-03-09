package com.shoshin.data.remote.entities.categories

import com.shoshin.data.entities.categories.CategoryData
import com.shoshin.domain_abstract.common.Mapper

class CategoryDataRemoteMapper: Mapper<CategoryData, CategoryRemote>() {
    override fun mapTo(from: CategoryData): CategoryRemote =
        CategoryRemote(
            id = from.id,
            name = from.name,
            imageUrl = from.imageUrl,
            dishesIds = from.itemsIds
        )

    override fun mapFrom(from: CategoryRemote): CategoryData =
        CategoryData(
            id = from.id,
            name = from.name,
            imageUrl = from.imageUrl,
            itemsIds = from.dishesIds ?: hashMapOf()
        )
}