package com.example.submissionintermediate.options

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityOptionsBinding
import com.example.submissionintermediate.main.MainActivity
import com.example.submissionintermediate.settings.SessionListener
import com.example.submissionintermediate.settings.ViewModelFactory


class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var viewModel: OptionsViewModel
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[OptionsViewModel::class.java]
        setupAction()
        logoutApp()
        supportActionBar?.hide()
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
                val loginSession = SessionListener(this)
                loginSession.logoutSession()
                viewModel.logout()
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