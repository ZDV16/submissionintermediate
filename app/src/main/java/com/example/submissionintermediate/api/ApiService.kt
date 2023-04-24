package com.example.submissionintermediate.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/v1/register")
    fun postRegister(@Field("name") name: String, @Field("email") email: String, @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/v1/login")
    fun postLogin( @Field("email") email: String, @Field("password") password: String
    ): Call<LoginResult>
}