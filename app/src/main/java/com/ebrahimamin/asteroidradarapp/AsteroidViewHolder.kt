package com.ebrahimamin.asteroidradarapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AsteroidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val asteroidName: TextView = itemView.findViewById(R.id.asteroid_name)
    val asteroidDetails: TextView = itemView.findViewById(R.id.asteroid_details)
    val asteroidStatusIcon: ImageView = itemView.findViewById(R.id.asteroid_status_icon)
}