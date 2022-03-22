package com.shoshin.restaurant.di.data.order

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class OrderModule {

}

//@InstallIn(SingletonComponent::class)
//@Module
//class LocationModule {
//    @Provides
//    fun provideLocationDao(appDatabase: AppDatabase): LocationDao = appDatabase.locationDbo()
//    @Provides
//    fun provideDataDbMapper(): Mapper<LocationData, LocationDbo> = LocationDataDbMapper()
//    @Provides
//    fun provideDataRemoteMapper(): Mapper<LocationData, LocationRemote> = LocationDataRemoteMapper()
//    @Provides
//    fun provideDomainDataMapper(): Mapper<Location, LocationData> = LocationDomainDataMapper()
//}