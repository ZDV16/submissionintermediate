package com.example.submissionintermediate.register

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.api.ApiConfig
import com.example.submissionintermediate.api.RegisterItem
import com.example.submissionintermediate.api.RegisterResponse
import com.example.submissionintermediate.data.StoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val repo: StoryRepository) : ViewModel() {
    fun doRegister(name: String, email: String, password: String) =
        repo.requestRegister(name, email, password)
}
