package com.example.submissionintermediate.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.submissionintermediate.api.ApiConfig
import com.example.submissionintermediate.api.LoginRequest
import com.example.submissionintermediate.api.LoginResponse
import com.example.submissionintermediate.api.LoginResult
import com.example.submissionintermediate.data.StoryRepository
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repo: StoryRepository) : ViewModel() {
    fun goLogin(loginReq : LoginRequest) = repo.requestLogin(loginReq)
    fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            repo.saveUserData(userModel)
        }
    }
}