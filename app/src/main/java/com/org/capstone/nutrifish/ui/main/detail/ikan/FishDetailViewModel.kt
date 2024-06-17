package com.org.capstone.nutrifish.ui.main.detail.ikan

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.data.local.repository.FishRepository
import com.org.capstone.nutrifish.data.remote.repository.StoryRepo
import com.org.capstone.nutrifish.data.remote.response.ListStoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FishDetailViewModel(context: Context, private val storyRepo: StoryRepo) : ViewModel() {

    private val fishRepository: FishRepository = FishRepository(context)

    init {
        fishRepository
    }

    fun getFishByName(fishName: String, callback: (FishEntity) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val fishEntity = fishRepository.getFishByName(fishName)
            callback(fishEntity)
        }
    }

    fun getRecipeByTitle(fishName: String): Flow<PagingData<ListStoryItem>> {
        return storyRepo.getAllStories().asFlow().map { pagingData ->
            pagingData.filter { story ->
                story.storyTitle?.contains(fishName, ignoreCase = true) == true
            }
        }.cachedIn(viewModelScope)
    }
}