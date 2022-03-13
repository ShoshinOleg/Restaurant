package com.shoshin.restaurant.ui.fragments.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.domain_abstract.usecases.locations.IGetLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: IGetLocationsUseCase
): ViewModel() {
    private val mutableLocations: MutableLiveData<Reaction<List<Location>>> = MutableLiveData()
    val locations = mutableLocations as LiveData<Reaction<List<Location>>>

    fun getLocations(needRemoteDownload: Boolean = false) {
        viewModelScope.launch {
            mutableLocations.value = Reaction.Progress()
            getLocationsUseCase.execute(needRemoteDownload).collect() {
                mutableLocations.value = it
            }
        }
    }
}