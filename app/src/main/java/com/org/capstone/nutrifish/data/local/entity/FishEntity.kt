package com.org.capstone.nutrifish.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FishEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "fishID")
    var fishId: Int = 0,

    @ColumnInfo(name = "fishName")
    var fishName: String? = null,

    @ColumnInfo(name = "fishDescription")
    var fishDescription: String? = null,

    @ColumnInfo(name = "fishBenefits")
    var fishBenefits: String? = null,

    @ColumnInfo(name = "fishImage")
    var fishImage: String? = null
) : Parcelable