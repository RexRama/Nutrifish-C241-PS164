package com.org.capstone.nutrifish.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.org.capstone.nutrifish.data.remote.api.ApiService
import com.org.capstone.nutrifish.data.remote.response.UserStoriesItem

class MyStoriesPaging(private val apiService: ApiService,
    private val userID: String) : PagingSource<Int, UserStoriesItem>() {
    override fun getRefreshKey(state: PagingState<Int, UserStoriesItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserStoriesItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMyStories(userID, position, params.loadSize)

            LoadResult.Page(
                data = responseData.userStories,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.userStories.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Error loading data", exception)
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val TAG = "MyStoriesPaging"
    }
}