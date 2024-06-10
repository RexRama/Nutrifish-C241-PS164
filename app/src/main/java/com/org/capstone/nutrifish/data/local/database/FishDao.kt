package com.org.capstone.nutrifish.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.capstone.nutrifish.data.local.entity.FishEntity

@Dao
interface FishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFish(fish: List<FishEntity>)

    @Query("SELECT * FROM fishentity")
    fun getAllFish(): List<FishEntity>

    @Query("DELETE FROM fishentity")
    fun deleteAllFish()

    @Query("SELECT * FROM fishentity WHERE fishName = :fishName")
    fun getFishByName(fishName: String): FishEntity

}