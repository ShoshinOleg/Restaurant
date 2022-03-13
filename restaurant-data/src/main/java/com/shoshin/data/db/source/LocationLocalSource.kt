package com.shoshin.data.db.source

import com.shoshin.data.db.entities.locations.LocationDao
import com.shoshin.data.db.entities.locations.LocationDbo
import com.shoshin.data.entities.locations.LocationData
import com.shoshin.data.interfaces.sources.local.ILocationLocalSource
import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.entities.locations.Location
import javax.inject.Inject

class LocationLocalSource @Inject constructor(
    private val locationDao: LocationDao,
    private val mapper: Mapper<LocationData, LocationDbo>
): ILocationLocalSource {
    override suspend fun getAll(): List<LocationData> {
        val locationDboList = locationDao.getLocations()
        return mapper.mapFrom(locationDboList)
    }

    override suspend fun insert(location: LocationData) =
        locationDao.insert(
            mapper.mapTo(location)
        )

    override suspend fun insertAll(locations: List<LocationData>) {
        val locationDboList = mapper.mapTo(locations)
        locationDao.insertAll(locationDboList)
    }

    override suspend fun deleteAll() = locationDao.deleteAll()

    override suspend fun delete(location: Location) {
        location.id?.let {
            locationDao.delete(it)
        }
    }
}