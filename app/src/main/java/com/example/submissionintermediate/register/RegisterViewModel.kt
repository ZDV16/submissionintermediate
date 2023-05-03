package com.example.submissionintermediate.register

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.api.ApiConfig
import com.example.submissionintermediate.api.RegisterItem
import com.example.submissionintermediate.api.RegisterResponse
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
                    _hasil.postValue(true)
                    _userRegister.postValue(response.body()?.registerItem)
                } else {
                    _hasil.postValue(false)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _hasil.postValue(false)
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}
