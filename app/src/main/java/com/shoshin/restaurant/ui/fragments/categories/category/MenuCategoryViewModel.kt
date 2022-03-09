package com.shoshin.restaurant.ui.fragments.categories.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.usecases.dish.IGetDishesByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuCategoryViewModel @Inject constructor(
    private val getDishesByCategoryUseCase: IGetDishesByCategoryUseCase
): ViewModel() {
    private var mutableDishes: MutableLiveData<Reaction<List<Dish>>>
            = MutableLiveData()
    val dishes = mutableDishes as LiveData<Reaction<List<Dish>>>

    fun getDishes(category: Category) {
        viewModelScope.launch {
            mutableDishes.value = Reaction.Progress()
            mutableDishes.value = getDishesByCategoryUseCase.execute(category)
        }
    }
}