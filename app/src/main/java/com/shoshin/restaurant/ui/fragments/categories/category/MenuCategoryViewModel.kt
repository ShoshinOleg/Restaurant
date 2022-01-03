package com.shoshin.restaurant.ui.fragments.categories.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.usecases.category.IGetCategoryUseCase
import com.shoshin.domain_abstract.usecases.dish.IGetDishesByCategoryUseCase
import com.shoshin.restaurant.main.app.App
import com.shoshin.restaurant.ui.common.ViewModelEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuCategoryViewModel(application: Application): AndroidViewModel(application) {
    @Inject
    lateinit var getCategoryUseCase: IGetCategoryUseCase
    @Inject
    lateinit var getDishesByCategoryUseCase: IGetDishesByCategoryUseCase

    private var mutableCategory: MutableLiveData<ViewModelEvent<MenuCategory>>
        = MutableLiveData()
    val category = mutableCategory as LiveData<ViewModelEvent<MenuCategory>>

    private var mutableDishes: MutableLiveData<ViewModelEvent<List<Dish>>>
            = MutableLiveData()
    val dishes = mutableDishes as LiveData<ViewModelEvent<List<Dish>>>

    init {
        (application as App).appComponent.inject(this)
    }

    fun getCategory(category: MenuCategory) {
        viewModelScope.launch {
            mutableCategory.value = ViewModelEvent.Download()
            when(val result = getCategoryUseCase.execute(category.id!!)) {
                is Reaction.Success -> {
                    mutableCategory.value = ViewModelEvent.Success(result.data)
                    getDishesSuspend(result.data)
                }
                is Reaction.Error -> {
                    mutableCategory.value = ViewModelEvent.Error(response =  result.errorResponse)
                }
            }
        }
    }

    fun getDishes(category: MenuCategory) {
        viewModelScope.launch {
            mutableDishes.value = ViewModelEvent.Download()
            when(val result = getDishesByCategoryUseCase.execute(category)) {
                is Reaction.Success -> {
                    mutableDishes.value = ViewModelEvent.Success(result.data)
                }
                is Reaction.Error -> {
                    mutableDishes.value = ViewModelEvent.Error(response = result.errorResponse)
                }
            }
        }
    }

    private suspend fun getDishesSuspend(category: MenuCategory) {
        mutableDishes.value = ViewModelEvent.Download()
        when(val result = getDishesByCategoryUseCase.execute(category)) {
            is Reaction.Success -> {
                mutableDishes.value = ViewModelEvent.Success(result.data)
            }
            is Reaction.Error -> {
                mutableDishes.value = ViewModelEvent.Error(response = result.errorResponse)
            }
        }
    }

}