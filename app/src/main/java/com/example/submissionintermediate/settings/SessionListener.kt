package com.example.submissionintermediate.settings

import android.content.Context
import com.example.submissionintermediate.R

class SessionListener(context: Context) {

    private val loginSession =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun logoutSession() {
        loginSession.edit().clear().apply()
    }
}