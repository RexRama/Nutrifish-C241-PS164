package com.org.capstone.nutrifish.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val uid: String,
    val name: String,
    val username: String?,
    val email: String,
    val password: String,
    val isLogin: Boolean,
    val token: String,
    val isGoogle: Boolean,
    val photoUrl: String?
) : Parcelable
