package com.org.capstone.nutrifish.data.remote.response

import com.google.gson.annotations.SerializedName

data class MyPostResponse(

	@field:SerializedName("userStories")
	val userStories: List<ListStoryItem>  ,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

