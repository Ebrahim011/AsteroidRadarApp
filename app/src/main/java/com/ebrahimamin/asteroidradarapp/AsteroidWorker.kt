package com.ebrahimamin.asteroidradarapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException

class AsteroidWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val asteroidDao = database.asteroidDao

        return try {
            val response = NasaApi.service.getNearEarthObjects("2023-11-01", "2023-11-08")
            val asteroids = response.near_earth_objects.values.flatten().map {
                AsteroidEntity(
                    id = it.id,
                    name = it.id,
                    absoluteMagnitude = it.absolute_magnitude_h,
                    estimatedDiameter = it.estimated_diameter.kilometers.estimated_diameter_max,
                    isPotentiallyHazardous = it.is_potentially_hazardous_asteroid,
                    closeApproachDate = it.close_approach_data[0].close_approach_date,
                    relativeVelocity = it.close_approach_data[0].relative_velocity.kilometers_per_second,
                    distanceFromEarth = it.close_approach_data[0].miss_distance.astronomical
                )
            }
            asteroidDao.insertAll(asteroids)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}