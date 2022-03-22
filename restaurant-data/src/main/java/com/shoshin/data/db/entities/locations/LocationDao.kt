package com.shoshin.data.db.entities.locations

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    suspend fun getLocations(): List<LocationDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationDbo>)

    @Query("DELETE FROM locations")
    suspend fun deleteAll()

    @Query("DELETE FROM locations WHERE id=:id")
    suspend fun delete(id: String)
}