package com.ebrahimamin.asteroidradarapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AsteroidAdapter(private val asteroids: List<NearEarthObject>) : RecyclerView.Adapter<AsteroidViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asteroid, parent, false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroids[position]
        holder.asteroidName.text = asteroid.id
        holder.asteroidDetails.text = "Magnitude: ${asteroid.absolute_magnitude_h}"

        val statusIconRes = if (asteroid.is_potentially_hazardous_asteroid) {
            R.drawable.ic_status_potentially_hazardous
        } else {
            R.drawable.ic_status_normal
        }
        holder.asteroidStatusIcon.setImageResource(statusIconRes)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AsteroidDetailActivity::class.java).apply {
                putExtra("asteroid", asteroid)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = asteroids.size
}