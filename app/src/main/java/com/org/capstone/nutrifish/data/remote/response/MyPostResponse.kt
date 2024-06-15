package com.org.capstone.nutrifish.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MyPostResponse(

	@field:SerializedName("userStories")
	val userStories: List<UserStoriesItem>  ,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
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
	val storyDescription: String? = null,

	@field:SerializedName("storyUsername")
	val storyUsername: String? = null
) : Parcelable
