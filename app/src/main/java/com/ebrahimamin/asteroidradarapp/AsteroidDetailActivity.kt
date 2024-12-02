package com.ebrahimamin.asteroidradarapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class AsteroidDetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asteroid_detail)

        val asteroidStatusImage: ImageView = findViewById(R.id.asteroid_status_image)
        val asteroidName: TextView = findViewById(R.id.asteroid_name)
        val closeApproachDate: TextView = findViewById(R.id.close_approach_date)
        val absoluteMagnitude: TextView = findViewById(R.id.absolute_magnitude)
        val estimatedDiameter: TextView = findViewById(R.id.estimated_diameter)
        val relativeVelocity: TextView = findViewById(R.id.relative_velocity)
        val distanceFromEarth: TextView = findViewById(R.id.distance_from_earth)
        val auHelpIcon: ImageView = findViewById(R.id.au_help_icon)

        val asteroid = intent.getParcelableExtra<AsteroidEntity>("asteroid")

        asteroid?.let {
            asteroidName.text = it.name
            closeApproachDate.text = it.closeApproachDate
            absoluteMagnitude.text = it.absoluteMagnitude.toString()
            estimatedDiameter.text = it.estimatedDiameter.toString()
            relativeVelocity.text = it.relativeVelocity
            distanceFromEarth.text = it.distanceFromEarth

            if (it.isPotentiallyHazardous) {
                asteroidStatusImage.setImageResource(R.drawable.asteroid_hazardous)
                asteroidStatusImage.contentDescription = getString(R.string.hazardous_asteroid)
            } else {
                asteroidStatusImage.setImageResource(R.drawable.asteroid_safe)
                asteroidStatusImage.contentDescription = getString(R.string.non_hazardous_asteroid)
            }
        }

        auHelpIcon.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.the_astronomical_unit_au_is_a_unit_of_length_roughly_the_distance_from_earth_to_the_sun_and_equal_to_about_150_million_kilometres_93_million_miles))
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
        auHelpIcon.contentDescription = getString(R.string.help_icon_explaining_the_astronomical_unit_au)
    }
}