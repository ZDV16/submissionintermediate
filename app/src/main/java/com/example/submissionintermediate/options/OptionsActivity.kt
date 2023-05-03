package com.example.submissionintermediate.options

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityOptionsBinding
import com.example.submissionintermediate.main.MainActivity
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import com.example.submissionintermediate.settings.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private val pref by lazy { UserPreferences.getInstance(dataStore) }
    private val viewModel: OptionsViewModel by viewModels {
        ViewModelFactory(pref)
    }
    private lateinit var user: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setUserModel()
        logoutApp()
        supportActionBar?.hide()
    }

    private fun setUserModel() {
        viewModel.getUser().observe(this) { user ->
            this.user = user
        }
    }

    private fun setupAction() {
        binding.btnLang.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun logoutApp() {
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.popUp)
            builder.setCancelable(false)
            builder.setPositiveButton(R.string.confirm) { dialog, which ->
                viewModel.logout(user)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            builder.setNegativeButton(R.string.negative) { dialog, which ->
                dialog.cancel()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}