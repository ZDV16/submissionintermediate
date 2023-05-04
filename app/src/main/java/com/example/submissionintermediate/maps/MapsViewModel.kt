package com.example.submissionintermediate.maps

import androidx.lifecycle.ViewModel
import com.example.submissionintermediate.data.StoryRepository

class MapsViewModel(private val repo: StoryRepository) : ViewModel() {
    fun getStoryLocation(token: String) =
        repo.getStoryLocation(token)
}