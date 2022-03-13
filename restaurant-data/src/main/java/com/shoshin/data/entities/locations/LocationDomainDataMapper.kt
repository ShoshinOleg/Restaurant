package com.shoshin.data.entities.locations

import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.entities.locations.Location

class LocationDomainDataMapper: Mapper<Location, LocationData>() {
    override fun mapTo(from: Location) =
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

    override fun mapFrom(from: LocationData) =
        Location(
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