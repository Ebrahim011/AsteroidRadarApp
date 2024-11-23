package com.ebrahimamin.asteroidradarapp

import android.os.Parcelable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.parcelize.Parcelize


private const val BASE_URL = "https://api.nasa.gov/"
private const val API_KEY = "7HzIUaVCoHsDXSs1IYwtccZxQ0mnBSwJ2fngBhZg"

@Parcelize
data class NearEarthObject(
    val id: String,
    val absolute_magnitude_h: Double,
    val estimated_diameter: EstimatedDiameter,
    val is_potentially_hazardous_asteroid: Boolean,
    val close_approach_data: List<CloseApproachData>
) : Parcelable

@Parcelize
data class EstimatedDiameter(
    val kilometers: DiameterRange
) : Parcelable

@Parcelize
data class DiameterRange(
    val estimated_diameter_max: Double
) : Parcelable

@Parcelize
data class CloseApproachData(
    val close_approach_date: String,
    val relative_velocity: Velocity,
    val miss_distance: Distance
) : Parcelable

@Parcelize
data class Velocity(val kilometers_per_second: String) : Parcelable

@Parcelize
data class Distance(val astronomical: String) : Parcelable

// Data class for APOD response
data class ApodResponse(
    val url: String,
    val media_type: String,
    val title: String
)

// Retrofit interface
interface NasaApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getNearEarthObjects(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): NasaNeoResponse

    @GET("planetary/apod")
    suspend fun getImageOfTheDay(
        @Query("api_key") apiKey: String = API_KEY
    ): ApodResponse
}

// Singleton for Retrofit
object NasaApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: NasaApiService by lazy { retrofit.create(NasaApiService::class.java) }
}

// Wrapper for NEO Response
data class NasaNeoResponse(
    val near_earth_objects: Map<String, List<NearEarthObject>>
)
