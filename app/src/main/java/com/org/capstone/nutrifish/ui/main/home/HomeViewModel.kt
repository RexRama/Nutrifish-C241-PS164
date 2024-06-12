package com.org.capstone.nutrifish.ui.main.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.data.local.repository.FishRepository
import com.org.capstone.nutrifish.data.remote.repository.StoryRepo
import com.org.capstone.nutrifish.data.remote.response.ListStoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(context: Context, provideStoryRepo: StoryRepo) : ViewModel() {
    private val fishRepository: FishRepository = FishRepository(context)
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val storyRepo = provideStoryRepo



    init {
        fishRepository
        storyRepo
    }
    fun fetchStories(): LiveData<PagingData<ListStoryItem>> {
        val stories = storyRepo.getAllStories().cachedIn(viewModelScope)
        return stories
    }



    fun getAllFish(onFishListReady: (List<FishEntity>) -> Unit) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val fishList = fishRepository.getAllFishDetail()
            _loading.postValue(false)
            onFishListReady(fishList)
        }
    }

 }