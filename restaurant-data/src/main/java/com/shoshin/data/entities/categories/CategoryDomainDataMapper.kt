package com.shoshin.data.entities.categories

import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.entities.category.Category

class CategoryDomainDataMapper: Mapper<Category, CategoryData>() {
    override fun mapTo(from: Category): CategoryData =
        CategoryData(
            id = from.id,
            name = from.name,
            imageUrl = from.imageURL,
            itemsIds = from.itemsIds
        )

    override fun mapFrom(from: CategoryData): Category =
        Category(
            id = from.id,
            name = from.name,
            imageURL = from.imageUrl,
            itemsIds = from.itemsIds
        )
}