package com.example.submissionintermediate.maps

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionintermediate.R
import com.example.submissionintermediate.addStory.AddStoryViewModel
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.databinding.ActivityMapsBinding
import com.example.submissionintermediate.settings.ApiResult
import com.example.submissionintermediate.settings.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: MapsViewModel
    private lateinit var addviewModel: AddStoryViewModel
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        viewModel = ViewModelProvider(this, factory)[MapsViewModel::class.java]
        addviewModel = ViewModelProvider(this, factory)[AddStoryViewModel::class.java]

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        supportActionBar?.hide()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val dicodingSpace = LatLng(-6.8957643, 107.6338462)
        mMap.addMarker(
            MarkerOptions()
                .position(dicodingSpace)
                .title("Dicoding Space")
                .snippet("Batik Kumeli No.50")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dicodingSpace, 15f))
        addManyMarker()
    }

    private fun addManyMarker() {
        addviewModel.getUser().observe(this) { it ->
            val token = "Bearer ${it.token}"
            viewModel.getStoryLocation(token).observe(this) {
                when (it) {
                    is ApiResult.Loading -> {}
                    is ApiResult.Success -> showMarker(it.data.listStory)
                    is ApiResult.Error -> Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showMarker(listStory: List<ListStoryItem>) {
        for (story in listStory) {
            val latlng = LatLng(story.lat, story.lon)
            mMap.addMarker(
                MarkerOptions()
                    .position(latlng)
                    .snippet(story.description)
                    .title(story.name)
            )
        }
    }
}