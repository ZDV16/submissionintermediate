package com.example.submissionintermediate.api


data class RegisterResponse(
    val registerItem: List<RegisterItem>,
    val error: String,
    val message:String
)

data class RegisterItem(
    val name: String,
    val email:String,
    val password:String
)
