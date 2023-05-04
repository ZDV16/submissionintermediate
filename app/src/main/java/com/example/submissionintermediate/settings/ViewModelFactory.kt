package com.example.submissionintermediate.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionintermediate.addStory.AddStoryViewModel
import com.example.submissionintermediate.data.StoryRepository
import com.example.submissionintermediate.di.Injection
import com.example.submissionintermediate.login.LoginViewModel
import com.example.submissionintermediate.main.MainViewModel
import com.example.submissionintermediate.maps.MapsViewModel
import com.example.submissionintermediate.options.OptionsViewModel
import com.example.submissionintermediate.register.RegisterViewModel

class ViewModelFactory(private val repo: StoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repo) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repo) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repo) as T
            }

            modelClass.isAssignableFrom(OptionsViewModel::class.java) -> {
                OptionsViewModel(repo) as T
            }

            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(repo) as T
            }

            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repo) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}