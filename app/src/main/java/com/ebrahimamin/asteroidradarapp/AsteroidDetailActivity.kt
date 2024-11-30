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
            asteroidName.text = "Asteroid : " + it.name
            closeApproachDate.text = it.closeApproachDate
            absoluteMagnitude.text = "${it.absoluteMagnitude} au"
            estimatedDiameter.text = "${it.estimatedDiameter} km"
            relativeVelocity.text = "${it.relativeVelocity} km/s"
            distanceFromEarth.text = "${it.distanceFromEarth} au"

            val statusImageRes = if (it.isPotentiallyHazardous) {
                R.drawable.asteroid_hazardous
            } else {
                R.drawable.asteroid_safe
            }
            asteroidStatusImage.setImageResource(statusImageRes)
            asteroidStatusImage.contentDescription = if (it.isPotentiallyHazardous) {
                getString(R.string.image_showing_the_status_of_the_asteroid_whether_it_is_hazardous_or_safe)
            } else {
                getString(R.string.image_showing_the_status_of_the_asteroid_whether_it_is_hazardous_or_safe)
            }
        }

        auHelpIcon.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Astronomical Unit (au)")
                .setMessage("The astronomical unit (au) is a unit of length, roughly the distance from Earth to the Sun and equal to about 150 million kilometres (93 million miles).")
                .setPositiveButton("OK", null)
                .show()
        }
        auHelpIcon.contentDescription = getString(R.string.help_icon_explaining_the_astronomical_unit_au)
    }
}