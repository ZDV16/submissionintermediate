package com.example.submissionintermediate.register

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.api.ApiConfig
import com.example.submissionintermediate.api.RegisterItem
import com.example.submissionintermediate.api.RegisterResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userRegister = MutableLiveData<List<RegisterItem>>()
    val userRegister: LiveData<List<RegisterItem>> = _userRegister

    private val _hasil = MutableLiveData<Boolean>()
    val hasil: LiveData<Boolean> = _hasil


    fun addUser(name: String, email: String, password: String) {
        _isLoading.value = true
        val userSelect = ApiConfig.getApiService().postRegister(name, email, password)
        userSelect.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _hasil.value = true
                    _userRegister.value = response.body()?.registerItem
                } else {
                    _hasil.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _hasil.value = false
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}
