package com.example.submissionintermediate.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import com.example.submissionintermediate.R
import com.example.submissionintermediate.customview.EditTextEmail
import com.example.submissionintermediate.databinding.ActivityMainBinding
import com.example.submissionintermediate.databinding.ActivityRegisterBinding
import com.example.submissionintermediate.databinding.ActivityWelcomeBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReg.setOnClickListener {
            registerClicked()
            Toast.makeText(
                this, if (viewModel.hasil1) {
                    "REGISTRATION FAILED"
                } else {
                    "REGISTRATION SUCCESS"
                }, Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun registerClicked() {
        viewModel.addUser(
            binding.etNama.text.toString(),
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
    }
}
