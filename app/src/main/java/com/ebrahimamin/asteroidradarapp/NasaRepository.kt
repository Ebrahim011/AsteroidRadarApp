package com.ebrahimamin.asteroidradarapp

class NasaRepository {
    suspend fun fetchNearEarthObjects(startDate: String, endDate: String) =
        NasaApi.service.getNearEarthObjects(startDate, endDate)

    suspend fun fetchImageOfTheDay() = NasaApi.service.getImageOfTheDay()
}