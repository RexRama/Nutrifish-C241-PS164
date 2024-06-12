package com.org.capstone.nutrifish.data.di

import android.content.Context
import com.org.capstone.nutrifish.data.remote.api.ApiConfig
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.data.remote.repository.MyStoryRepo
import com.org.capstone.nutrifish.data.remote.repository.StoryRepo
import com.org.capstone.nutrifish.utils.SettingPreferences
import com.org.capstone.nutrifish.utils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideStoryRepo() : StoryRepo {
        val apiService = ApiConfig.apiService()
        return StoryRepo(apiService)
    }

    fun provideMyStoryRepo(context: Context) : MyStoryRepo {
        val apiService = ApiConfig.apiService()
        val pref = SettingPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        val userID = user.uid
        return MyStoryRepo(apiService, userID)
    }
}