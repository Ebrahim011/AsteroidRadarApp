package com.ebrahimamin.asteroidradarapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageOfTheDay: ImageView = findViewById(R.id.image_of_the_day)
        val titleImage: TextView = findViewById(R.id.title_image)
        val asteroidList: RecyclerView = findViewById(R.id.asteroid_list)

        asteroidList.layoutManager = LinearLayoutManager(this)

        viewModel.imageOfTheDay.observe(this, Observer { apod ->
            Glide.with(this).load(apod.url).into(imageOfTheDay)
            titleImage.text = apod.title
        })

        viewModel.asteroids.observe(this, Observer { asteroids ->
            asteroidList.adapter = AsteroidAdapter(asteroids)
        })
    }
}