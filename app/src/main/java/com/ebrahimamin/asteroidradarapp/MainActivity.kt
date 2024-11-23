package com.ebrahimamin.asteroidradarapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var asteroidAdapter: AsteroidAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val imageOfTheDay: ImageView = findViewById(R.id.image_of_the_day)
        val titleImage: TextView = findViewById(R.id.title_image)
        val asteroidList: RecyclerView = findViewById(R.id.asteroid_list)

        asteroidAdapter = AsteroidAdapter(emptyList()) { asteroid ->
            val intent = Intent(this, AsteroidDetailActivity::class.java).apply {
                putExtra("asteroid", asteroid)
            }
            startActivity(intent)
        }
        asteroidList.layoutManager = LinearLayoutManager(this)
        asteroidList.adapter = asteroidAdapter

        viewModel.imageOfTheDay.observe(this, Observer { apod ->
            Glide.with(this).load(apod.url).into(imageOfTheDay)
            titleImage.text = apod.title
        })

        viewModel.asteroids.observe(this, Observer { asteroids ->
            asteroidAdapter.updateData(asteroids)
        })

        viewModel.fetchAsteroidsFromApiAndCache()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d("MainActivity", "onCreateOptionsMenu called")
        menuInflater.inflate(R.menu.time_filters, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MainActivity", "onOptionsItemSelected called with itemId: ${item.itemId}")
        return when (item.itemId) {
            R.id.filter_by_today -> {
                Log.d("MainActivity", "Fetching today's asteroids")
                Toast.makeText(this, "Fetching today's asteroids", Toast.LENGTH_SHORT).show()
                viewModel.fetchAsteroidsForToday()
                true
            }
            R.id.filter_by_week -> {
                Log.d("MainActivity", "Fetching this week's asteroids")
                Toast.makeText(this, "Fetching this week's asteroids", Toast.LENGTH_SHORT).show()
                viewModel.fetchAsteroidsForThisWeek()
                true
            }
            R.id.filter_by_past_month -> {
                Log.d("MainActivity", "Fetching past month's asteroids")
                Toast.makeText(this, "Fetching past month's asteroids", Toast.LENGTH_SHORT).show()
                viewModel.fetchAsteroidsForPastMonth()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}