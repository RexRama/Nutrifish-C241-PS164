package com.org.capstone.nutrifish.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.org.capstone.nutrifish.data.remote.api.ApiService
import com.org.capstone.nutrifish.data.remote.response.ListStoryItem

class StoriesPaging(private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStories(position, params.loadSize)

            val stories = responseData.listStory.distinctBy { it.storyID }

            if (responseData.listStory.isEmpty()) {
                return LoadResult.Error(NoDataException("No data available"))
            }

            val nextKey = if (responseData.listStory.size < params.loadSize) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = stories,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Error loading data", exception)
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val TAG = "PagingSource"
    }

}