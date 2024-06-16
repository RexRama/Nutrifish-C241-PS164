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
            Log.d(TAG, "Loading page: $position with load size: ${params.loadSize}")
            val responseData = apiService.getMyStories(userID, position, params.loadSize)
            Log.d(TAG, "Response received: ${responseData.userStories.size} items")

            if (responseData.userStories.isEmpty()) {
                Log.d(TAG, "No data available for userID: $userID at position: $position")
                return LoadResult.Error(NoDataException("No data available"))
            }

            val nextKey = if (responseData.userStories.size < params.loadSize) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = responseData.userStories,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            ).also {
                Log.d(TAG, "Page loaded successfully: $it")
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Error loading data", exception)
            LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val TAG = "MyStoriesPaging"
    }
}

class NoDataException(message: String) : Exception(message)