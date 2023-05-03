package com.example.submissionintermediate.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.submissionintermediate.databinding.ActivityDetailBinding
import com.example.submissionintermediate.settings.UserPreferences
import com.example.submissionintermediate.settings.ViewModelFactory
import com.squareup.picasso.Picasso

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val pref by lazy { UserPreferences.getInstance(dataStore) }
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory(pref)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                token = user.token
                showStory()
            }
        }
    }

    private fun showStory() {
        val id = intent.getStringExtra(EXTRA_ID)
        if (id != null) {
            viewModel.getStoryDetail(token, id)
        }
        viewModel.storyDetail.observe(this) { storyDetail ->
            binding.apply {
                tvName.text = storyDetail.name
                tvDesc.text = storyDetail.description
                Picasso.get()
                    .load(storyDetail.photoUrl)
                    .into(ivStory)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar4.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}