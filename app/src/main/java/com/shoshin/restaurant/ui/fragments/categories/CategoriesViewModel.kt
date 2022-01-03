package com.shoshin.restaurant.ui.fragments.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.domain_abstract.usecases.category.IGetCategoriesUseCase
import com.shoshin.restaurant.main.app.App
import com.shoshin.restaurant.ui.common.ViewModelEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoriesViewModel(application: Application): AndroidViewModel(application) {
    @Inject
    lateinit var getCategoriesUseCase: IGetCategoriesUseCase

    private var mutableCategories: MutableLiveData<ViewModelEvent<List<MenuCategory>>>
        = MutableLiveData()
    val categories = mutableCategories as LiveData<ViewModelEvent<List<MenuCategory>>>

    init {
        (application as App).appComponent.inject(this)
    }

    fun getCategories() {
        viewModelScope.launch {
            mutableCategories.value = ViewModelEvent.Download()
            when(val result = getCategoriesUseCase.execute()) {
                is Reaction.Success -> {
                    Log.e("cats=", "cats=${result.data}")
                    mutableCategories.value = ViewModelEvent.Success(result.data)
                }
                is Reaction.Error -> {
                    mutableCategories.value = ViewModelEvent.Error(response = result.errorResponse)
                }
            }
        }
    }

}