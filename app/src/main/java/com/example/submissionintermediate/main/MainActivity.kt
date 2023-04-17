package com.example.submissionintermediate.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityMainBinding
import com.example.submissionintermediate.settings.UserPreferences
import com.example.submissionintermediate.settings.ViewModelFactory
import com.example.submissionintermediate.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pref by lazy { UserPreferences.getInstance(dataStore) }
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(pref)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

    }


    private fun setupViewModel() {
        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                binding.tvUsername.text = getString(R.string.welcome)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }
}