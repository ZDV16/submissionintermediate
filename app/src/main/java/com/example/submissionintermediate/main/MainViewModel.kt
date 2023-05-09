package com.example.submissionintermediate.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.data.StoryRepository

class MainViewModel(private val repo: StoryRepository) : ViewModel() {
    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return repo.getStory().cachedIn(viewModelScope)
    }
}