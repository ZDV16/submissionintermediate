package com.example.submissionintermediate.addStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.data.StoryRepository
import com.example.submissionintermediate.settings.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun uploadFile(token: String, file: MultipartBody.Part, description: RequestBody) =
        storyRepository.addStory(token, file, description)

    fun getUser(): LiveData<UserModel> {
        return storyRepository.getUserData()
    }
}