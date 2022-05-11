package com.shoshin.restaurant.ui.fragments.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.domain_abstract.usecases.category.IGetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: IGetCategoriesUseCase
): ViewModel() {
    private var mutableCategories = MutableLiveData<Reaction<List<Category>>>()
    val categories = mutableCategories as LiveData<Reaction<List<Category>>>

    fun getCategories(needRemoteDownload: Boolean = false) {
        viewModelScope.launch(Dispatchers.Main) {
            getCategoriesUseCase.execute(needRemoteDownload).collect {
                withContext(Dispatchers.Main) {
                    mutableCategories.value = it
                }
            }
        }
    }
}