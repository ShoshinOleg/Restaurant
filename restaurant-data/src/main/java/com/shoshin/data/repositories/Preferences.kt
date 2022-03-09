package com.shoshin.data.repositories

import android.content.Context
import com.shoshin.domain_abstract.repositories.IPreferencesRepository
import javax.inject.Inject

class Preferences @Inject constructor(
    context: Context
): IPreferencesRepository {
    companion object {
        private const val RESTAURANT_PREFERENCES = "RESTAURANT_PREFERENCES"
    }

    private val sharedPreferences =
        context.getSharedPreferences(RESTAURANT_PREFERENCES, Context.MODE_PRIVATE)

    fun setStringField(field: String, value: String?) {
        sharedPreferences
            .edit()
            .putString(field, value)
            .apply()
    }

    override fun setLongField(field: String, value: Long) {
        sharedPreferences
            .edit()
            .putLong(field, value)
            .apply()
    }

    override fun getLongField(field: String, defValue: Long) : Long {
        return sharedPreferences.getLong(field, defValue)
    }
}