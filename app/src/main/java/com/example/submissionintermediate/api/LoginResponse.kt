package com.example.submissionintermediate.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginRequest(
    @SerializedName("email")
    @Expose
    val email: String? = null,

    @SerializedName("password")
    @Expose
    val password: String? = null
)

data class LoginResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)
