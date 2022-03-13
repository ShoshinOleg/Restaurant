package com.shoshin.data.remote.entities.locations

import com.shoshin.data.entities.locations.LocationData
import com.shoshin.domain_abstract.common.Mapper

class LocationDataRemoteMapper: Mapper<LocationData, LocationRemote>() {
    override fun mapTo(from: LocationData) =
        LocationRemote(
            id = from.id,
            street = from.street,
            house = from.house,
            flat = from.flat,
            entrance = from.entrance,
            intercomCode = from.intercomCode,
            level = from.level,
            comment = from.comment,
            coordinate = from.coordinate,
            toCoordinate = from.toCoordinate
        )

    override fun mapFrom(from: LocationRemote) =
        LocationData(
            id = from.id,
            street = from.street,
            house = from.house,
            flat = from.flat,
            entrance = from.entrance,
            intercomCode = from.intercomCode,
            level = from.level,
            comment = from.comment,
            coordinate = from.coordinate,
            toCoordinate = from.toCoordinate
        )
}