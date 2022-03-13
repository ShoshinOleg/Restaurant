package com.shoshin.restaurant.di.data.location

import com.shoshin.data.db.entities.locations.LocationDao
import com.shoshin.data.db.entities.locations.LocationDataDbMapper
import com.shoshin.data.db.entities.locations.LocationDbo
import com.shoshin.data.db.main.AppDatabase
import com.shoshin.data.entities.locations.LocationData
import com.shoshin.data.entities.locations.LocationDomainDataMapper
import com.shoshin.data.remote.entities.locations.LocationDataRemoteMapper
import com.shoshin.data.remote.entities.locations.LocationRemote
import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.entities.locations.Location
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class LocationModule {
    @Provides
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao = appDatabase.locationDbo()
    @Provides
    fun provideDataDbMapper(): Mapper<LocationData, LocationDbo> = LocationDataDbMapper()
    @Provides
    fun provideDataRemoteMapper(): Mapper<LocationData, LocationRemote> = LocationDataRemoteMapper()
    @Provides
    fun provideDomainDataMapper(): Mapper<Location, LocationData> = LocationDomainDataMapper()
}