package com.example.submissionintermediate.options

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submissionintermediate.settings.UserModel
import com.example.submissionintermediate.settings.UserPreferences
import kotlinx.coroutines.launch

class OptionsViewModel(private val pref: UserPreferences) : ViewModel() {

    fun logout(userModel: UserModel) {
        viewModelScope.launch {
            pref.logout(userModel)
        }
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}