package com.org.capstone.nutrifish.data.remote.api

import com.org.capstone.nutrifish.data.remote.model.LoginModel
import com.org.capstone.nutrifish.data.remote.model.RegisterModel
import com.org.capstone.nutrifish.data.remote.response.LoginResponse
import com.org.capstone.nutrifish.data.remote.response.MyPostResponse
import com.org.capstone.nutrifish.data.remote.response.RegisterResponse
import com.org.capstone.nutrifish.data.remote.response.StoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //User Registration
    @POST("register")
    fun registerUser(
        @Body user: RegisterModel
    ): Call<RegisterResponse>

    //User Login
    @POST("login")
    fun loginUser(
        @Body user: LoginModel
    ): Call<LoginResponse>

    //Get All stories
    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse


    //Get all stories by userID
    @GET("stories/user/{id}")
    suspend fun getMyStories(
        @Path("id") userID: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): MyPostResponse
}