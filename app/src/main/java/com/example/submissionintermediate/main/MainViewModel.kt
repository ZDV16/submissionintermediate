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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userRegister = MutableLiveData<List<RegisterItem>>()

    var hasil1: Boolean = true

    fun login(name: String, email: String, password: String) {
        _isLoading.value = true
        val userSelect = ApiConfig.getApiService().postRegister(name, email, password)
        userSelect.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    hasil1 = false
                    _userRegister.value = response.body()?.registerItem
                } else {
                    hasil1 = true
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                hasil1 = true
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


}