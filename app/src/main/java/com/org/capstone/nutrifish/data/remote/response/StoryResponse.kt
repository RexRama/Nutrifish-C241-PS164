package com.org.capstone.nutrifish.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class StoryResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem>,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class ListStoryItem(

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

	@field:SerializedName("userID")
	val userID: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
