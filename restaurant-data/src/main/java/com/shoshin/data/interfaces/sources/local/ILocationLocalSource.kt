package com.shoshin.data.interfaces.sources.local

import com.shoshin.data.entities.locations.LocationData
import com.shoshin.domain_abstract.entities.locations.Location

interface ILocationLocalSource {
    suspend fun getAll(): List<LocationData>
    suspend fun insert(location: LocationData)
    suspend fun insertAll(locations: List<LocationData>)
    suspend fun deleteAll()
    suspend fun delete(location: Location)
}