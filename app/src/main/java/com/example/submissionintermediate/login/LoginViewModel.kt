package com.example.submissionintermediate.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.submissionintermediate.api.ApiConfig
import com.example.submissionintermediate.api.LoginResponse
import com.example.submissionintermediate.api.LoginResult
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreferences) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userLogin = MutableLiveData<LoginResult>()
    val userLogin: LiveData<LoginResult> = _userLogin

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _hasil = MutableLiveData<Boolean>()
    val hasil: LiveData<Boolean> = _hasil


    fun pressLogin(email: String, password: String) {
        _isLoading.value = true
        val userSelect = ApiConfig.getApiService().postLogin(email, password)
        userSelect.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _hasil.postValue(true)
                    _userLogin.postValue(response.body()?.loginResult)
                    _token.postValue(response.body()?.loginResult?.token)
                } else {
                    _hasil.postValue(false)
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            pref.saveUser(userModel)
        }
    }
}