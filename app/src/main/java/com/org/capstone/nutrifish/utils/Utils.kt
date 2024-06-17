package com.org.capstone.nutrifish.utils

import android.content.Context
import android.content.Intent
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.data.remote.response.ListStoryItem
import com.org.capstone.nutrifish.ui.auth.register.RegisterActivity
import com.org.capstone.nutrifish.ui.auth.welcome.WelcomeActivity
import com.org.capstone.nutrifish.ui.main.MainActivity
import com.org.capstone.nutrifish.ui.main.upload.UploadRecipeActivity

class Utils {

    fun toWelcome(context: Context) {
        val toWelcome = Intent(context, WelcomeActivity::class.java)
        context.startActivity(toWelcome)
    }
    fun toRegister(context: Context) {
        val toRegister = Intent(context, RegisterActivity::class.java)
        context.startActivity(toRegister)
    }

    fun toHome(context: Context){
        val toHome = Intent(context, MainActivity::class.java)
        context.startActivity(toHome)
    }

    fun toUpload(context: Context) {
        val toUpload = Intent(context, UploadRecipeActivity::class.java)
        context.startActivity(toUpload)
    }


    interface OnItemClickCallback {
        fun onFishClicked(data: FishEntity)
        fun onPostClicked(data: ListStoryItem)
    }


}