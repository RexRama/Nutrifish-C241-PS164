package com.org.capstone.nutrifish.data.local.repository

import android.content.Context
import com.org.capstone.nutrifish.data.local.database.FishDao
import com.org.capstone.nutrifish.data.local.database.FishDatabase
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FishRepository(context: Context) {
    private val fishDao: FishDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FishDatabase.getDatabase(context)
        fishDao = db.fishDao()
    }

    fun getAllFishDetail(): List<FishEntity> = fishDao.getAllFish()

    suspend fun getFishByName(fishName: String): FishEntity = withContext(Dispatchers.IO) {
        fishDao.getFishByName(fishName)
    }
}