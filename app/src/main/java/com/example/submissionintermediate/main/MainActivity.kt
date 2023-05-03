package com.example.submissionintermediate.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionintermediate.adapter.StoriesAdapter
import com.example.submissionintermediate.addStory.AddStoryActivity
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.databinding.ActivityMainBinding
import com.example.submissionintermediate.detail.DetailActivity
import com.example.submissionintermediate.maps.MapsActivity
import com.example.submissionintermediate.options.OptionsActivity
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import com.example.submissionintermediate.settings.ViewModelFactory
import com.example.submissionintermediate.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoriesAdapter
    private val pref by lazy { UserPreferences.getInstance(dataStore) }
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(pref)
    }
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUserModel()
        setupViewModel()
        intentOptions()
        adapter = StoriesAdapter()
        adapter.setOnItemClickCallback(object : StoriesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                val detail = Intent(this@MainActivity, DetailActivity::class.java)
                    .putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(detail)
            }
        })
        intentMaps()
        intentAdd()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        supportActionBar?.hide()
    }

    private fun setUserModel() {
        viewModel.getUser().observe(this) { user ->
            this.user = user
        }
    }

    private fun setupViewModel() {
        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                showRecyclerView()
                observableViewModel()
                showStories()
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

    private fun observableViewModel() {
        viewModel.storyItem.observe(this) { stories ->
            if (stories != null) {
                adapter.setList(stories as ArrayList<ListStoryItem>)
            }
        }
    }

    private fun showStories() {
        viewModel.token.observe(this) {
            user.token = it
        }
        viewModel.getStories(user.token)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}