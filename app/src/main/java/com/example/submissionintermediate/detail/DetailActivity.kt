package com.example.submissionintermediate.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.databinding.ActivityDetailBinding
import com.example.submissionintermediate.settings.UserPreferences
import com.example.submissionintermediate.settings.ViewModelFactory
import com.squareup.picasso.Picasso

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding


    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val detail = intent.getParcelableExtra<ListStoryItem>(EXTRA_NAME) as ListStoryItem

        binding.apply {
            tvName.text = detail.name
            tvDesc.text = detail.description
            Picasso.get()
                .load(detail.photoUrl)
                .into(ivStory)
        }
        supportActionBar?.hide()
    }





    private fun showLoading(isLoading: Boolean) {
        binding.progressBar4.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}