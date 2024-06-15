package com.org.capstone.nutrifish.data.remote.api

import android.content.ClipDescription
import com.org.capstone.nutrifish.data.remote.model.LoginModel
import com.org.capstone.nutrifish.data.remote.model.RegisterModel
import com.org.capstone.nutrifish.data.remote.response.LoginResponse
import com.org.capstone.nutrifish.data.remote.response.MyPostResponse
import com.org.capstone.nutrifish.data.remote.response.RegisterResponse
import com.org.capstone.nutrifish.data.remote.response.StoryResponse
import com.org.capstone.nutrifish.data.remote.response.UploadStoryResponse
import com.org.capstone.nutrifish.data.remote.response.VerifyGoogleTokenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    //Upload Story
    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization")token: String,
        @Part photo: MultipartBody.Part,
        @Part("storyTitle") storyTitle: RequestBody,
        @Part("storyDescription") storyDescription: RequestBody,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?,
    ) : Call<UploadStoryResponse>

    @POST("verify-google-token")
    fun verifyGoogleToken(
        @Body idToken: RequestBody
    ): Call<VerifyGoogleTokenResponse>


}