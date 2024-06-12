package com.org.capstone.nutrifish.data.remote.model

data class UserModel(
    val name: String,
    val username: String?,
    val email: String,
    val password: String,
    val isLogin: Boolean,
    val token: String
)
