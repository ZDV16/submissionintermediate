package com.example.submissionintermediate.options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionintermediate.data.StoryRepository
import kotlinx.coroutines.launch

class OptionsViewModel(private val repo: StoryRepository) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}