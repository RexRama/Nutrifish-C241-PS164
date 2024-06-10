package com.org.capstone.nutrifish.data.local.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [FishEntity::class], version = 1)
abstract class FishDatabase : RoomDatabase() {

    abstract fun fishDao(): FishDao

    companion object {
        @Volatile
        private var INSTANCE: FishDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FishDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FishDatabase::class.java,
                    "fish_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    insertData(database, context.applicationContext)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        @SuppressLint("DiscouragedApi")
        fun insertData(database: FishDatabase, context: Context) {
            val dao = database.fishDao()
            val resources = context.resources
            val fishNames = resources.getStringArray(R.array.fish_names)
            val fishDescriptions = resources.getStringArray(R.array.fish_descriptions)
            val fishBenefits = resources.getStringArray(R.array.fish_benefits)
            val fishImages = resources.getStringArray(R.array.fish_images)

            val fishEntities = mutableListOf<FishEntity>()
            for (i in fishNames.indices) {
                fishEntities.add(
                    FishEntity(
                        i,
                        fishNames[i],
                        fishDescriptions[i],
                        fishBenefits[i],
                        fishImages[i]
                    )
                )
            }


            dao.insertFish(fishEntities)
        }
    }
}
