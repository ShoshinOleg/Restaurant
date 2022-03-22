package com.shoshin.data.interfaces.sources.remote

import com.shoshin.data.entities.locations.LocationData
import com.shoshin.domain_abstract.common.Reaction

interface ILocationRemoteSource {
    suspend fun getLocations(): Reaction<List<LocationData>>
    suspend fun setLocation(location: LocationData): Reaction<LocationData>
    suspend fun removeLocation(location: LocationData): Reaction<LocationData>
}