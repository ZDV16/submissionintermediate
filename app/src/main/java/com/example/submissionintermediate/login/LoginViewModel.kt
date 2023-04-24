package com.example.submissionintermediate.login

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.example.submissionintermediate.api.*
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreferences) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userLogin = MutableLiveData<LoginResult>()
    val userLogin: LiveData<LoginResult> = _userLogin

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    private val _hasil = MutableLiveData<Boolean>()
    val hasil: LiveData<Boolean> = _hasil
    

    fun pressLogin(email: String, password: String) {
        _isLoading.value = true
        val userSelect = ApiConfig.getApiService().postLogin(email, password)
        userSelect.enqueue(object : Callback<LoginResult> {
            override fun onResponse(
                call: Call<LoginResult>,
                response: Response<LoginResult>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _hasil.postValue(true)
                    _userLogin.value = response.body()
                } else {

                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}