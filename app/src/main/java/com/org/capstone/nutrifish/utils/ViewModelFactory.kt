package com.org.capstone.nutrifish.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.org.capstone.nutrifish.ui.auth.register.RegisterViewModel
import com.org.capstone.nutrifish.ui.main.detail.ikan.FishDetailViewModel
import com.org.capstone.nutrifish.ui.main.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(DialogUtils(context)) as T
        } else if (modelClass.isAssignableFrom(FishDetailViewModel::class.java)){
            return FishDetailViewModel(context) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}