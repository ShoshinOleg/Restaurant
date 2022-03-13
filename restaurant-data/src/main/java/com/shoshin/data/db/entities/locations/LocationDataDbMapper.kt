package com.shoshin.data.db.entities.locations

import com.shoshin.data.entities.locations.LocationData
import com.shoshin.domain_abstract.common.Mapper

class LocationDataDbMapper: Mapper<LocationData, LocationDbo>() {
    override fun mapTo(from: LocationData) =
        LocationDbo(
            id = from.id ?: "null",
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

    override fun mapFrom(from: LocationDbo) =
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