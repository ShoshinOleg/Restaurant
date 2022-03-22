package com.shoshin.domain_abstract.repositories

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import kotlinx.coroutines.flow.Flow

interface ILocationRepository {
    suspend fun getLocations(needRemoteDownload: Boolean): Flow<Reaction<List<Location>>>
    suspend fun setLocation(location: Location): Flow<Reaction<Location>>
    suspend fun removeLocation(location: Location): Flow<Reaction<Location>>
}