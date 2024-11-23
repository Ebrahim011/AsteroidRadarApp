package com.ebrahimamin.asteroidradarapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<AsteroidEntity>)

    @Query("SELECT * FROM asteroid_table ORDER BY closeApproachDate ASC")
    suspend fun getAllAsteroids(): List<AsteroidEntity>
}