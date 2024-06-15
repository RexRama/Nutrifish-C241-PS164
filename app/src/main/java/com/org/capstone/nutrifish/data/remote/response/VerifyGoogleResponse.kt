package com.org.capstone.nutrifish.data.remote.response

import com.google.gson.annotations.SerializedName

data class VerifyGoogleTokenResponse(
	@SerializedName("error") val error: Boolean,
	@SerializedName("message") val message: String,
	@SerializedName("token") val token: String? // JWT token if successful
)
