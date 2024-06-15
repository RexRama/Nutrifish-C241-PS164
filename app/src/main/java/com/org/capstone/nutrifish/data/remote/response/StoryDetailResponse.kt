package com.org.capstone.nutrifish.data.remote.response

import com.google.gson.annotations.SerializedName

data class StoryDetailResponse(

	@field:SerializedName("storyPhotoUrl")
	val storyPhotoUrl: String,

	@field:SerializedName("storyDateCreated")
	val storyDateCreated: StoryDateCreated,

	@field:SerializedName("storyTitle")
	val storyTitle: String,

	@field:SerializedName("lon")
	val lon: Any,

	@field:SerializedName("storyDescription")
	val storyDescription: String,

	@field:SerializedName("userID")
	val userID: String,

	@field:SerializedName("lat")
	val lat: Any
)

data class StoryDateCreated(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)
