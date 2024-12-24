package com.themoviedbdemo.ui

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.themoviedbdemo.R
import com.themoviedbdemo.adapter.KnownForAdapter
import com.themoviedbdemo.databinding.ActivityMainBinding
import com.themoviedbdemo.databinding.ActivityPersonDetailsBinding
import com.themoviedbdemo.models.responcemodel.Person

class PersonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar
        setSupportActionBar(binding.toolbar)

        // Set Toolbar text color programmatically
        binding.toolbar.setTitleTextColor(Color.WHITE)


        // Set up the action bar with the back arrow
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // Enable back arrow
            title = "Person Details"
        }

        // Set back arrow color programmatically
        val backArrow = binding.toolbar.navigationIcon
        backArrow?.setTint(Color.WHITE)

        // Get the passed Person data
        val person = intent.getParcelableExtra<Person>("person")
        if (person != null) {
            displayPersonDetails(person)
        }


    }
    // Handle the back arrow click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { // ID of the back arrow
                onBackPressed()   // Navigate back
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun displayPersonDetails(person: Person) {
        // Set basic personal info
        binding.personName.text = person.name
        binding.personPopularity.text = "Popularity: ${person.popularity}"
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${person.profile_path}")
            .into(binding.personImage)

        // Set up grid view for images
        val imagesAdapter = KnownForAdapter(person.known_for ?: emptyList())
        binding.knownForRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.knownForRecyclerView.adapter = imagesAdapter
    }
}