package com.ebrahimamin.asteroidradarapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebrahimamin.asteroidradarapp.ApodResponse
import com.ebrahimamin.asteroidradarapp.NasaRepository
import com.ebrahimamin.asteroidradarapp.NearEarthObject
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = NasaRepository()

    private val _asteroids = MutableLiveData<List<NearEarthObject>>()
    val asteroids: LiveData<List<NearEarthObject>> get() = _asteroids

    private val _imageOfTheDay = MutableLiveData<ApodResponse>()
    val imageOfTheDay: LiveData<ApodResponse> get() = _imageOfTheDay

    init {
        fetchAsteroids()
        fetchImageOfTheDay()
    }

    private fun fetchAsteroids() {
        viewModelScope.launch {
            try {
                val response = repository.fetchNearEarthObjects("2023-11-01", "2023-11-08")
                _asteroids.postValue(response.near_earth_objects.values.flatten())
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
}