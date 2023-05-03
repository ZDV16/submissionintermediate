package com.example.submissionintermediate.addStory

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissionintermediate.api.ApiConfig
import com.example.submissionintermediate.api.FileUploadResponse
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStoryViewModel(private val pref: UserPreferences) : ViewModel() {

    private val _storyUpload = MutableLiveData<FileUploadResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _hasil = MutableLiveData<Boolean>()
    val hasil: LiveData<Boolean> = _hasil

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }


    fun uploadFile(getFile: File, token: String, descriptionText: String) {

        val file = reduceFileImage(getFile)
        val description =
            descriptionText.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        _isLoading.value = true
        val bearer = "Bearer $token"
        val story = ApiConfig.getApiService().uploadImage(bearer, imageMultipart, description)
        story.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _hasil.postValue(true)
                    _storyUpload.postValue(response.body())
                } else {
                    _hasil.postValue(false)
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                _hasil.postValue(false)
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}