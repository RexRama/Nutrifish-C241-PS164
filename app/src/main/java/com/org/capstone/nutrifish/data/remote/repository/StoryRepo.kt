package com.org.capstone.nutrifish.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.org.capstone.nutrifish.data.remote.api.ApiService
import com.org.capstone.nutrifish.data.remote.paging.StoriesPaging
import com.org.capstone.nutrifish.data.remote.response.ListStoryItem

class StoryRepo(private val apiService: ApiService) {

    private var token = ""

    fun setToken(token: String) {
        this.token = token
    }

    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                StoriesPaging(apiService)
            }
        ).liveData
    }
}