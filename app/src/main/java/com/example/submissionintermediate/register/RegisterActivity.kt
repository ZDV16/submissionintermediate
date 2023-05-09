package com.example.submissionintermediate.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityRegisterBinding
import com.example.submissionintermediate.settings.ApiResult
import com.example.submissionintermediate.settings.ViewModelFactory


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.btnReg.setOnClickListener {
            registerClicked()
        }

        supportActionBar?.hide()
    }

    private fun registerClicked() {
        if (binding.etPassword.text?.length!! < 8) {
            Toast.makeText(this, R.string.errorPassword, Toast.LENGTH_SHORT).show()
        } else {
            showLoading(true)
            viewModel.postRegister(
                binding.etNama.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            ).observe(this) {
                when (it) {
                    is ApiResult.Success -> {
                        showLoading(false)
                        Toast.makeText(this, R.string.regYes, Toast.LENGTH_SHORT).show()
                    }

                    is ApiResult.Loading -> showLoading(true)
                    is ApiResult.Error -> {
                        showLoading(false)
                        Toast.makeText(this, R.string.regNo, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
