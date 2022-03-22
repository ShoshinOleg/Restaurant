package com.shoshin.restaurant.ui.fragments.locations.location_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.domain_abstract.usecases.locations.ISetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationAddViewModel @Inject constructor(
    private val setLocationUseCase: ISetLocationUseCase
): ViewModel() {
    private val mutableLocation = MutableLiveData<Reaction<Location>>()
    val locations = mutableLocation as LiveData<Reaction<Location>>

    fun setLocation(location: Location) {
        viewModelScope.launch {
            mutableLocation.value = Reaction.Progress()
            setLocationUseCase.execute(location).collect {
                mutableLocation.value = it
            }
        }
    }
}