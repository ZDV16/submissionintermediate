package com.example.submissionintermediate.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionintermediate.R
import com.example.submissionintermediate.api.LoginRequest
import com.example.submissionintermediate.databinding.ActivityLoginBinding
import com.example.submissionintermediate.main.MainActivity
import com.example.submissionintermediate.settings.ApiResult
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        binding.button.setOnClickListener {
            loginClicked()
        }


        supportActionBar?.hide()
    }

    private fun loginClicked() {
        val creds = LoginRequest(
            binding.etUsernameLogin.text.toString(),
            binding.etPasswordLogin.text.toString()
        )
        showLoading(true)

        viewModel.goLogin(creds).observe(this) {
            when (it) {
                is ApiResult.Success -> {
                    showLoading(false)
                    Toast.makeText(this, R.string.loginYes, Toast.LENGTH_SHORT).show()
                    val response = it.data
                    saveUserData(
                        UserModel(
                            response.loginResult.token.toString(),
                            true
                        )
                    )
                    val mainPage = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(mainPage)
                    finish()
                }

                is ApiResult.Loading -> showLoading(true)
                is ApiResult.Error -> {
                    Toast.makeText(this, R.string.loginNo, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }

                else -> {
                    Toast.makeText(this, R.string.errorsys, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun saveUserData(user: UserModel) {
        viewModel.saveUser(user)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}


