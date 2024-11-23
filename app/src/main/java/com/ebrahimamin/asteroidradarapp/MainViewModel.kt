package com.ebrahimamin.asteroidradarapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NasaRepository()

    private val _asteroids = MutableLiveData<List<AsteroidEntity>>()
    val asteroids: LiveData<List<AsteroidEntity>> get() = _asteroids

    private val _imageOfTheDay = MutableLiveData<ApodResponse>()
    val imageOfTheDay: LiveData<ApodResponse> get() = _imageOfTheDay

    init {
        fetchImageOfTheDay()
        fetchAsteroidsFromApiAndCache()
    }

    fun fetchAsteroidsFromApiAndCache() {
        viewModelScope.launch {
            try {
                val response = repository.fetchNearEarthObjects(getTodayDate(), getNextWeekDate())
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
                _asteroids.postValue(asteroids)
                saveAsteroidsToDatabase(asteroids)
            } catch (e: Exception) {
                e.printStackTrace()
                fetchAsteroidsFromDatabase()
            }
        }
    }

    fun fetchAsteroidsForToday() {
        viewModelScope.launch {
            try {
                val response = repository.fetchNearEarthObjects(getTodayDate(), getTodayDate())
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
                _asteroids.postValue(asteroids)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchAsteroidsForThisWeek() {
        viewModelScope.launch {
            try {
                val response = repository.fetchNearEarthObjects(getTodayDate(), getNextWeekDate())
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
                _asteroids.postValue(asteroids)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchAsteroidsForPastMonth() {
        viewModelScope.launch {
            try {
                val response = repository.fetchNearEarthObjects(getPastMonthDate(), getTodayDate())
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
                _asteroids.postValue(asteroids)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchAsteroidsFromDatabase() {
        viewModelScope.launch {
            try {
                val database = AsteroidDatabase.getInstance(getApplication())
                val allAsteroids = database.asteroidDao.getAllAsteroids()
                _asteroids.postValue(allAsteroids)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchImageOfTheDay() {
        viewModelScope.launch {
            try {
                val apod = repository.fetchImageOfTheDay()
                _imageOfTheDay.postValue(apod)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveAsteroidsToDatabase(asteroids: List<AsteroidEntity>) {
        viewModelScope.launch {
            try {
                val database = AsteroidDatabase.getInstance(getApplication())
                database.asteroidDao.insertAll(asteroids)
            } catch (e: Exception) {
                e.printStackTrace()
            }
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

    private fun getPastMonthDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        return sdf.format(calendar.time)
    }
}