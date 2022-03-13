package com.shoshin.data.db.entities.locations

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationDbo(
    @PrimaryKey
    var id: String,
    var street: String? = null,
    var house: String? = null,
    var flat: String? = null,
    var entrance: String? = null,
    var intercomCode: String? = null,
    var level: String? = null,
    var comment: String? = null,
    var coordinate: String? = null,
    var toCoordinate: Boolean = false
)