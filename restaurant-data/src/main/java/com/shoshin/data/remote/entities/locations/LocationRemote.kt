package com.shoshin.data.remote.entities.locations

data class LocationRemote(
    var id: String? = null,
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