package com.shoshin.data.repositories

import android.util.Log
import com.shoshin.data.entities.locations.LocationData
import com.shoshin.data.interfaces.sources.local.ILocationLocalSource
import com.shoshin.data.interfaces.sources.remote.ILocationRemoteSource
import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.domain_abstract.repositories.ILocationRepository
import com.shoshin.domain_abstract.repositories.IPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val localLocationSource: ILocationLocalSource,
    private val remoteLocationSource: ILocationRemoteSource,
    private val locationDomainDataMapper: Mapper<Location, LocationData>,
    private val preferences: IPreferencesRepository
): ILocationRepository {

    companion object {
        const val PREFS_KEY_LOCATIONS_UPDATED_TIME = "PREFS_KEY_LOCATIONS_UPDATED_TIME"
    }

    override suspend fun getLocations(needRemoteDownload: Boolean): Flow<Reaction<List<Location>>> =
        flow {
            when {
                needRemoteDownload -> fetchRemote()
                isDataOutdated() -> fetchRemote()
                else -> fetchLocal()
            }
        }

    override suspend fun setLocation(location: Location): Flow<Reaction<Location>> =
        flow {
            emit(Reaction.Progress())
            val locationData = locationDomainDataMapper.mapTo(location)
            when(val reaction = remoteLocationSource.setLocation(locationData)) {
                is Reaction.Success -> {
                    localLocationSource.insert(reaction.data)
                    val abc = locationDomainDataMapper.mapFrom(reaction.data)
                    emit(
                        Reaction.Success(
                            abc
                        )
                    )
                }
                is Reaction.Error -> emit(Reaction.Error(reaction.message, reaction.exception))
                else -> {}
            }
        }

    private suspend fun FlowCollector<Reaction<List<Location>>>.fetchRemote() {
        if(isDataCached()) {
            val locations = localLocationSource.getAll()
            val localLocations = locationDomainDataMapper.mapFrom(locations)
            emit(Reaction.Progress(localLocations))
        } else {
            emit(Reaction.Progress())
        }

        when(val result = remoteLocationSource.getLocations()) {
            is Reaction.Success -> {
                localLocationSource.deleteAll()
                localLocationSource.insertAll(result.data)
                preferences.setLongField(PREFS_KEY_LOCATIONS_UPDATED_TIME, System.currentTimeMillis())
                val remoteLocations = locationDomainDataMapper.mapFrom(result.data)
                emit(Reaction.Success(remoteLocations))
            }
            is Reaction.Error -> emit(Reaction.Error(result.message, result.exception))
            else -> {}
        }
    }

    private suspend fun FlowCollector<Reaction<List<Location>>>.fetchLocal() {
        val locations = locationDomainDataMapper.mapFrom(localLocationSource.getAll())
        emit(Reaction.Success(locations))
    }

    private fun isDataCached(): Boolean =
        preferences.getLongField(PREFS_KEY_LOCATIONS_UPDATED_TIME, -1) != -1L

    private fun isDataOutdated(): Boolean {
        val lastDownloaded = preferences.getLongField(PREFS_KEY_LOCATIONS_UPDATED_TIME, -1)
        if(lastDownloaded == -1L) {
            return true
        } else {
            val currentTime = System.currentTimeMillis()
            val diffInMs = (currentTime - lastDownloaded)
            val diff: Long = TimeUnit.MILLISECONDS.toMinutes(diffInMs)
            Log.e("diff", "diff=$diff")
            return diff > 0
        }
    }
}