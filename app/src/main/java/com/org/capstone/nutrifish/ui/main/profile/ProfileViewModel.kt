package com.org.capstone.nutrifish.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.org.capstone.nutrifish.data.remote.repository.MyStoryRepo
import com.org.capstone.nutrifish.data.remote.response.UserStoriesItem

class ProfileViewModel(provideMyStoryRepo: MyStoryRepo) : ViewModel() {
    private val myStoryRepo = provideMyStoryRepo

    init {
        myStoryRepo
    }

    fun fetchMyStory(): LiveData<PagingData<UserStoriesItem>> {
        return myStoryRepo.getMyStories()
            .cachedIn(viewModelScope)
    }

}