package com.ebrahimamin.asteroidradarapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AsteroidDetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asteroid_detail)


        val asteroidStatusImage: ImageView = findViewById(R.id.asteroid_status_image)
        val asteroidName: TextView = findViewById(R.id.asteroid_name)
        val closeApproachDate: TextView = findViewById(R.id.close_approach_date)
        val absoluteMagnitude: TextView = findViewById(R.id.absolute_magnitude)
        val estimatedDiameter: TextView = findViewById(R.id.estimated_diameter)
        val relativeVelocity: TextView = findViewById(R.id.relative_velocity)
        val distanceFromEarth: TextView = findViewById(R.id.distance_from_earth)
        val auHelpIcon: ImageView = findViewById(R.id.au_help_icon)

        val asteroid = intent.getParcelableExtra<NearEarthObject>("asteroid")

        asteroid?.let {
            asteroidName.text = "Asteroid : " + it.id
            closeApproachDate.text = it.close_approach_data[0].close_approach_date
            absoluteMagnitude.text = "${it.absolute_magnitude_h} au"
            estimatedDiameter.text = "${it.estimated_diameter.kilometers.estimated_diameter_max} km"
            relativeVelocity.text = "${it.close_approach_data[0].relative_velocity.kilometers_per_second} km/s"
            distanceFromEarth.text = "${it.close_approach_data[0].miss_distance.astronomical} au"

            val statusImageRes = if (it.is_potentially_hazardous_asteroid) {
                R.drawable.asteroid_hazardous
            } else {
                R.drawable.asteroid_safe
            }
            asteroidStatusImage.setImageResource(statusImageRes)
        }

        auHelpIcon.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Astronomical Unit (au)")
                .setMessage("The astronomical unit (au) is a unit of length, roughly the distance from Earth to the Sun and equal to about 150 million kilometres (93 million miles).")
                .setPositiveButton("OK", null)
                .show()
        }
    }
}