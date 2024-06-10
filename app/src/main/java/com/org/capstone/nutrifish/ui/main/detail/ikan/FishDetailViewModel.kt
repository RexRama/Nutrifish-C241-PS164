package com.org.capstone.nutrifish.ui.main.detail.ikan

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.data.local.repository.FishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FishDetailViewModel(context: Context) : ViewModel(){

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
}