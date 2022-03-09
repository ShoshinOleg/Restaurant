package com.shoshin.domain_abstract.repositories

interface IPreferencesRepository {
    fun setLongField(field: String, value: Long)
    fun getLongField(field: String, defValue: Long) : Long
}