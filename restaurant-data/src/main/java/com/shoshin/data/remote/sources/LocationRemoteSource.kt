package com.shoshin.data.remote.sources

import android.util.Log
import com.shoshin.data.entities.locations.LocationData
import com.shoshin.data.interfaces.services.remote.ILocationRemoteService
import com.shoshin.data.interfaces.sources.remote.ILocationRemoteSource
import com.shoshin.data.remote.entities.locations.LocationRemote
import com.shoshin.data.remote.main.NetworkHelper
import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.common.Reaction
import javax.inject.Inject

class LocationRemoteSource @Inject constructor(
    private val locationService: ILocationRemoteService,
    private val locationDataRemoteMapper: Mapper<LocationData, LocationRemote>
): ILocationRemoteSource {
    override suspend fun getLocations(): Reaction<List<LocationData>> =
        NetworkHelper.safeApiCall {
            locationDataRemoteMapper.mapFrom(
                locationService.getLocations()
            )
        }

    override suspend fun setLocation(location: LocationData): Reaction<LocationData> =
        NetworkHelper.safeApiCall {
            val locationRemote = locationDataRemoteMapper.mapTo(location)
            locationDataRemoteMapper.mapFrom(
                locationService.setLocation(locationRemote)
            )
        }

    override suspend fun removeLocation(location: LocationData): Reaction<LocationData> =
        if(location.id != null) {
            Log.e("r1", "r1")
            NetworkHelper.safeApiCall {
                Log.e("r2", "r2")
                Log.e("location.id", "location.id=${location.id}")
                val abc = locationService.removeLocation(location.id!!)
                Log.e("r3", "r3")
                locationDataRemoteMapper.mapFrom(
                    abc
                )
            }
        } else {
            Log.e("r5", "r5")
            Reaction.Error(message = "Не удалось удалить. Не задан идентификатор адреса")
        }
}