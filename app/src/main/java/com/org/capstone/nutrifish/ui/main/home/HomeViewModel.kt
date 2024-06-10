package com.org.capstone.nutrifish.ui.main.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.data.local.repository.FishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {
    private val fishRepository: FishRepository = FishRepository(context)
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading



    init {
        fishRepository
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