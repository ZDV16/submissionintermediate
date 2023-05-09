package com.example.submissionintermediate.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("/v1/login")
    suspend fun postlogin(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("/v1/register")
    suspend fun postRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @GET("/v1/stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null
    ): StoriesResponse

    @Multipart
    @POST("/v1/stories")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse


}