// AsteroidEntity.kt
package com.ebrahimamin.asteroidradarapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "asteroid_table")
data class AsteroidEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val isPotentiallyHazardous: Boolean,
    val closeApproachDate: String,
    val relativeVelocity: String,
    val distanceFromEarth: String
) : Parcelable