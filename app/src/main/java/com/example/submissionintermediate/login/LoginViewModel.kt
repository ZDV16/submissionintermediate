package com.example.submissionintermediate.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionintermediate.api.LoginRequest
import com.example.submissionintermediate.data.StoryRepository
import com.example.submissionintermediate.settings.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: StoryRepository) : ViewModel() {
    fun goLogin(loginReq: LoginRequest) = repo.requestLogin(loginReq)
    fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            repo.saveUserData(userModel)
        }
    }
}