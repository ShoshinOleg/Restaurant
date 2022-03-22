package com.shoshin.data.interfaces.services.remote

import com.shoshin.data.remote.entities.locations.LocationRemote
import com.shoshin.data.remote.main.Constants
import retrofit2.http.*

interface ILocationRemoteService {
    @GET(Constants.LOCATIONS_URL)
    suspend fun getLocations(): List<LocationRemote>

    @POST(Constants.LOCATIONS_URL)
    suspend fun setLocation(@Body location: LocationRemote): LocationRemote

    @DELETE("${Constants.LOCATIONS_URL}/{id}")
    suspend fun removeLocation(
        @Path(value = "id", encoded = true) id: String
    ): LocationRemote
}