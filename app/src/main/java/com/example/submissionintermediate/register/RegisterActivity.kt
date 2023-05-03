package com.example.submissionintermediate.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReg.setOnClickListener {
            registerClicked()
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        supportActionBar?.hide()
    }

    private fun registerClicked() {
        if (binding.etPassword.text?.length!! < 8) {
            Toast.makeText(this, R.string.errorPassword, Toast.LENGTH_SHORT).show()
        } else {
            viewModel.addUser(
                binding.etNama.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
            viewModel.hasil.observe(this) {
                if (it) {
                    Toast.makeText(this, R.string.regYes, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.regNo, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
