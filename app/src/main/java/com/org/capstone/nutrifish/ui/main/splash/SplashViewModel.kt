package com.org.capstone.nutrifish.ui.main.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.utils.SettingPreferences

class SplashViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}