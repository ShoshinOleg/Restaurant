package com.shoshin.data.db.entities.categories

import com.shoshin.data.entities.categories.CategoryData
import com.shoshin.domain_abstract.common.Mapper

class CategoryDataDbMapper: Mapper<CategoryData, CategoryDbo>() {
    override fun mapTo(from: CategoryData): CategoryDbo {
        return CategoryDbo(
            id = from.id ?: "null",
            name = from.name,
            imageUrl = from.imageUrl,
            itemsIds = from.itemsIds,
            downloadTime = from.downloadTime
        )
    }

    override fun mapFrom(from: CategoryDbo): CategoryData {
        return CategoryData(
            id = from.id,
            name = from.name,
            imageUrl = from.imageUrl,
            itemsIds = from.itemsIds,
            downloadTime = from.downloadTime
        )
    }
}