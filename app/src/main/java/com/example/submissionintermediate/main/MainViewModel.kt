package com.example.submissionintermediate.main

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.submissionintermediate.api.ApiConfig
import com.example.submissionintermediate.api.RegisterItem
import com.example.submissionintermediate.api.RegisterResponse
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreferences) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

}