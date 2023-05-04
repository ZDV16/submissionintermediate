package com.example.submissionintermediate.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submissionintermediate.api.ApiService
import com.example.submissionintermediate.api.FileUploadResponse
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.api.LoginRequest
import com.example.submissionintermediate.api.LoginResponse
import com.example.submissionintermediate.api.RegisterRequest
import com.example.submissionintermediate.api.RegisterResponse
import com.example.submissionintermediate.api.StoriesResponse
import com.example.submissionintermediate.settings.ApiResult
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(private val pref: UserPreferences, private val apiService: ApiService) {

    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StorySourcePaging(apiService, pref)
            }
        ).liveData
    }

    fun getStoryLocation(token: String): LiveData<ApiResult<StoriesResponse>> = liveData {
        emit(ApiResult.Loading)
        try {
            val response = apiService.getStoriesLocation(token, 1)
            emit(ApiResult.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(ApiResult.Error(e.message.toString()))
        }
    }

    fun requestLogin(loginReq: LoginRequest): LiveData<ApiResult<LoginResponse>> = liveData {
        emit(ApiResult.Loading)
        try {
            val response = apiService.postlogin(loginReq)
            emit(ApiResult.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(ApiResult.Error(e.message.toString()))
        }
    }

    fun requestRegister(name: String, email: String, password: String)
            : LiveData<ApiResult<RegisterResponse>> =
        liveData {
            emit(ApiResult.Loading)
            try {
                val response = apiService.postRegister(
                    RegisterRequest(name, email, password)
                )
                emit(ApiResult.Success(response))
            } catch (e: Exception) {
                Log.d("Signup", e.message.toString())
                emit(ApiResult.Error(e.message.toString()))
            }
        }

    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): LiveData<ApiResult<FileUploadResponse>> = liveData {
        emit(ApiResult.Loading)
        try {
            val response = apiService.uploadImage(token, file, description)
            emit(ApiResult.Success(response))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message.toString()))
        }
    }

    fun getUserData(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun saveUserData(user: UserModel) {
        pref.saveUser(user)
    }

    suspend fun logout() {
        pref.logout()
    }
}