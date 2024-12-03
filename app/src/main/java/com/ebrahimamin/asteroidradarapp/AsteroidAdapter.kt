// AsteroidAdapter.kt
package com.ebrahimamin.asteroidradarapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AsteroidAdapter(
    private var asteroids: List<AsteroidEntity>,
    private val onItemClick: (AsteroidEntity) -> Unit
) : RecyclerView.Adapter<AsteroidViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asteroid, parent, false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroids[position]
        holder.asteroidName.text = asteroid.name
        holder.asteroidDetails.text = "Magnitude: ${asteroid.absoluteMagnitude}, Diameter: ${asteroid.estimatedDiameter} km"
        val statusImageRes = if (asteroid.isPotentiallyHazardous) {
            holder.asteroidStatusIcon.contentDescription = holder.itemView.context.getString(R.string.hazardous_asteroid)
            R.drawable.ic_status_potentially_hazardous
        } else {
            holder.asteroidStatusIcon.contentDescription = holder.itemView.context.getString(R.string.non_hazardous_asteroid)
            R.drawable.ic_status_normal
        }
        holder.asteroidStatusIcon.setImageResource(statusImageRes)
        holder.itemView.setOnClickListener { onItemClick(asteroid) }
    }

    override fun getItemCount(): Int = asteroids.size

    fun updateData(newAsteroids: List<AsteroidEntity>) {
        asteroids = newAsteroids
        notifyDataSetChanged()
    }
}