package com.example.submissionintermediate.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.submissionintermediate.api.ApiConfig
import com.example.submissionintermediate.data.StoryRepository
import com.example.submissionintermediate.settings.UserPreferences

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(preferences, apiService)
    }
}