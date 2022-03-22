package com.shoshin.restaurant.ui.fragments.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.domain_abstract.usecases.locations.IGetLocationsUseCase
import com.shoshin.domain_abstract.usecases.locations.IRemoveLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: IGetLocationsUseCase,
    private val removeLocationUseCase: IRemoveLocationUseCase
): ViewModel() {
    private val mutableLocations: MutableLiveData<Reaction<List<Location>>> = MutableLiveData()
    val locations = mutableLocations as LiveData<Reaction<List<Location>>>

    private val mutableRemovedLocation = MutableSharedFlow<Reaction<Location>>()
    val removedLocation = mutableRemovedLocation as SharedFlow<Reaction<Location>>

    fun getLocations(needRemoteDownload: Boolean = false) {
        viewModelScope.launch {
            mutableLocations.value = Reaction.Progress()
            getLocationsUseCase.execute(needRemoteDownload).collect() {
                mutableLocations.value = it
            }
        }
    }

    fun removeLocation(location: Location) {
        viewModelScope.launch {
            mutableRemovedLocation.emit(Reaction.Progress())
            removeLocationUseCase.execute(location).collect() {
                mutableRemovedLocation.emit(it)
            }
        }
    }
}