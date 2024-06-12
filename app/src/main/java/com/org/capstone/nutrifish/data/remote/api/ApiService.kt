package com.org.capstone.nutrifish.data.remote.api

import com.org.capstone.nutrifish.data.remote.model.LoginModel
import com.org.capstone.nutrifish.data.remote.model.RegisterModel
import com.org.capstone.nutrifish.data.remote.response.LoginResponse
import com.org.capstone.nutrifish.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    fun registerUser(
        @Body user: RegisterModel
    ) : Call<RegisterResponse>

    @POST("login")
    fun loginUser(
        @Body user: LoginModel
    ) : Call<LoginResponse>
}