package com.example.submissionintermediate.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionintermediate.adapter.LoadingStoryAdapter
import com.example.submissionintermediate.adapter.StoriesAdapter
import com.example.submissionintermediate.addStory.AddStoryActivity
import com.example.submissionintermediate.addStory.AddStoryViewModel
import com.example.submissionintermediate.databinding.ActivityMainBinding
import com.example.submissionintermediate.maps.MapsActivity
import com.example.submissionintermediate.options.OptionsActivity
import com.example.submissionintermediate.settings.ViewModelFactory
import com.example.submissionintermediate.welcome.WelcomeActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoriesAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var addStoryViewModel: AddStoryViewModel
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        addStoryViewModel = ViewModelProvider(this, factory)[AddStoryViewModel::class.java]

        setupViewModel()

        adapter = StoriesAdapter()
        binding.rvListStory.layoutManager = LinearLayoutManager(this)
        binding.rvListStory.setHasFixedSize(true)
        intentMaps()
        intentAdd()
        intentOptions()

        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        addStoryViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                showRecyclerView()
                showList()
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun showRecyclerView() {
        binding.rvListStory.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvListStory.setHasFixedSize(true)
        binding.rvListStory.adapter = adapter
    }

    private fun showList() {
        binding.rvListStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStoryAdapter { adapter.retry() }
        )
        viewModel.getStories().observe(this@MainActivity) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun intentOptions() {
        binding.imgOptions.setOnClickListener {
            startActivity(Intent(this, OptionsActivity::class.java))
        }
    }

    private fun intentAdd() {
        binding.btnAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun intentMaps() {
        binding.btnMaps.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}