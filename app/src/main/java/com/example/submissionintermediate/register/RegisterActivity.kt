package com.example.submissionintermediate.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityMainBinding
import com.example.submissionintermediate.databinding.ActivityRegisterBinding
import com.example.submissionintermediate.databinding.ActivityWelcomeBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val viewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnReg.setOnClickListener {
            registerClicked()
            println("ANJING")
        }

    }

    private fun registerClicked(){
        viewModel.userRegister.observe(this) {
        viewModel.addUser(binding.etNama.toString(),binding.etEmail.toString(),binding.etPassword.toString())

        }

    }
}