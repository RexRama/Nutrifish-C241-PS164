package com.org.capstone.nutrifish.data.remote.response

import com.google.gson.annotations.SerializedName

data class MyPostResponse(

	@field:SerializedName("userStories")
	val userStories: List<UserStoriesItem>  ,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UserStoriesItem(

	@field:SerializedName("storyID")
	val storyID: String? = null,

	@field:SerializedName("storyPhotoUrl")
	val storyPhotoUrl: String? = null,

	@field:SerializedName("storyDateCreated")
	val storyDateCreated: String? = null,

	@field:SerializedName("storyTitle")
	val storyTitle: String? = null,

	@field:SerializedName("storyDescription")
	val storyDescription: String? = null
)
