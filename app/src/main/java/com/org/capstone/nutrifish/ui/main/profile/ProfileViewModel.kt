package com.org.capstone.nutrifish.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.data.remote.repository.MyStoryRepo
import com.org.capstone.nutrifish.data.remote.response.UserStoriesItem
import com.org.capstone.nutrifish.utils.SettingPreferences

class ProfileViewModel(private val preferences: SettingPreferences,provideMyStoryRepo: MyStoryRepo) : ViewModel() {
    private val myStoryRepo = provideMyStoryRepo

    init {
        myStoryRepo
    }

    fun getUser(): LiveData<UserModel> {
        return preferences.getUser().asLiveData()
    }

    fun fetchMyStory(): LiveData<PagingData<UserStoriesItem>> {
        return myStoryRepo.getMyStories()
            .cachedIn(viewModelScope)
    }

}