package com.shoshin.domain_abstract.repositories

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory

interface ICategoryRepository {
    suspend fun getCategories() : Reaction<List<MenuCategory>>
    suspend fun getCategory(id: String): Reaction<MenuCategory>
}