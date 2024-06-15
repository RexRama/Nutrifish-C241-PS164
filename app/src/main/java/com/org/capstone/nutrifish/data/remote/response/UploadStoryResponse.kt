package com.org.capstone.nutrifish.data.remote.response

import com.google.gson.annotations.SerializedName

data class UploadStoryResponse(

	@field:SerializedName("storyID")
	val storyID: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
