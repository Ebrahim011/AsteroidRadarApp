package com.ebrahimamin.asteroidradarapp

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.WorkerParameters
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequestBuilder
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class AsteroidWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val asteroidDao = database.asteroidDao

        return try {
            val response = NasaApi.service.getNearEarthObjects(getTodayDate(), getNextWeekDate())
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

    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getNextWeekDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        return sdf.format(calendar.time)
    }

    companion object {
        fun scheduleWork(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<AsteroidWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}