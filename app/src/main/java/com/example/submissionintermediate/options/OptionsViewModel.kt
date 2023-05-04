package com.example.submissionintermediate.options

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submissionintermediate.data.StoryRepository
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import kotlinx.coroutines.launch

class OptionsViewModel(private val repo: StoryRepository) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}