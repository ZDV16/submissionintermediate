package com.example.submissionintermediate.register

import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.data.StoryRepository

class RegisterViewModel(private val repo: StoryRepository) : ViewModel() {
    fun doRegister(name: String, email: String, password: String) =
        repo.requestRegister(name, email, password)
}
