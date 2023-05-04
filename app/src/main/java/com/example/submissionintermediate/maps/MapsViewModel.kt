package com.example.submissionintermediate.maps

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissionintermediate.api.ApiConfig
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.api.StoriesResponse
import com.example.submissionintermediate.data.StoryRepository
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val repo : StoryRepository) : ViewModel(){
    fun getStoryLocation(token: String) =
        repo.getStoryLocation(token)
}