package com.example.submissionintermediate.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityLoginBinding
import com.example.submissionintermediate.databinding.ActivityMainBinding
import com.example.submissionintermediate.main.MainActivity
import com.example.submissionintermediate.register.RegisterViewModel
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import com.example.submissionintermediate.welcome.WelcomeActivity
import kotlinx.coroutines.Job
import okhttp3.internal.wait

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUser().observe(this) {
                user-> this.user
        }

        binding.button.setOnClickListener {
            loginClicked()
        }

    }
    private fun loginClicked() {
        viewModel.pressLogin(
            binding.etUsernameLogin.text.toString(),
            binding.etPasswordLogin.text.toString()
        )
        viewModel.hasil.observe(this) {
            if(it){
                Toast.makeText(this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show()
                user.token = viewModel.token.toString()
                user.isLogin = true
                println(user.token)
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
