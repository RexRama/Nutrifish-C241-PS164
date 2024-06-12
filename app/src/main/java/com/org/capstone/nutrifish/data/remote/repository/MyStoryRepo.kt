package com.org.capstone.nutrifish.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.org.capstone.nutrifish.data.remote.api.ApiService
import com.org.capstone.nutrifish.data.remote.paging.MyStoriesPaging
import com.org.capstone.nutrifish.data.remote.response.UserStoriesItem

class MyStoryRepo(private val apiService: ApiService, private val userID: String) {

    fun getMyStories(): LiveData<PagingData<UserStoriesItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ), pagingSourceFactory = {
                MyStoriesPaging(apiService, userID)
            }
        ).liveData
    }
}