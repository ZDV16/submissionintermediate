package com.example.submissionintermediate.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.submissionintermediate.databinding.ActivityLoginBinding
import com.example.submissionintermediate.main.MainActivity
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import com.example.submissionintermediate.settings.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val pref by lazy { UserPreferences.getInstance(dataStore) }
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory(pref)
    }
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserModel()

        binding.button.setOnClickListener {
            loginClicked()
        }

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

    private fun loginClicked() {
        viewModel.pressLogin(
            binding.etUsernameLogin.text.toString(),
            binding.etPasswordLogin.text.toString()
        )
        viewModel.hasil.observe(this) {
            if (it) {
                Toast.makeText(this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show()
                getToken()
                val mainPage = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(mainPage)
                finish()
            } else {
                Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun getToken() {
        viewModel.token.observe(this) {
            user.token = it
            user.isLogin = true
        }
        viewModel.saveUser(user)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}


