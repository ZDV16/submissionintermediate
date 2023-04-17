package com.example.submissionintermediate.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/register")
    fun postRegister(@Field("name") name: String, @Field("email") email: String, @Field("password") password: String
    ): Call<RegisterResponse>
}