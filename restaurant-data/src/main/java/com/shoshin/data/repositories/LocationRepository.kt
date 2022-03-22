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
                    emit(
                        Reaction.Success(
                            locationDomainDataMapper.mapFrom(reaction.data)
                        )
                    )
                }
                is Reaction.Error -> emit(Reaction.Error(reaction.message, reaction.errorInfo))
                else -> {}
            }
        }

    override suspend fun removeLocation(location: Location): Flow<Reaction<Location>> =
        flow {
            Log.e("t1", "t1")
            emit(Reaction.Progress(location))
            Log.e("t2", "t2")

            val locationData = locationDomainDataMapper.mapTo(location)
            Log.e("t3", "t3")

            when(val reaction = remoteLocationSource.removeLocation(locationData)) {
                is Reaction.Success -> {
                    Log.e("t41", "t41")

                    localLocationSource.delete(location)
                    Log.e("t42", "t42")
                    val abc = locationDomainDataMapper.mapFrom(reaction.data)
                    Log.e("t43", "t43")
                    emit(
                        Reaction.Success(
                            abc
                        )
                    )
                    Log.e("t44", "t44")
                }
                is Reaction.Error -> {
                    Log.e("onErr", "onErr")
                    emit(Reaction.Error(reaction.message, reaction.errorInfo, data = location))
                }
                else -> {
                    Log.e("t5", "t5")
                }
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
            is Reaction.Error -> emit(Reaction.Error(result.message, result.errorInfo))
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