package com.org.capstone.nutrifish.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.org.capstone.nutrifish.data.di.Injection
import com.org.capstone.nutrifish.ui.auth.register.RegisterViewModel
import com.org.capstone.nutrifish.ui.auth.welcome.WelcomeViewModel
import com.org.capstone.nutrifish.ui.main.MainViewModel
import com.org.capstone.nutrifish.ui.main.detail.ikan.FishDetailViewModel
import com.org.capstone.nutrifish.ui.main.home.HomeViewModel
import com.org.capstone.nutrifish.ui.main.profile.ProfileViewModel
import com.org.capstone.nutrifish.ui.main.splash.SplashViewModel
import com.org.capstone.nutrifish.ui.main.upload.UploadViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val context: Context,
    private val pref: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(DialogUtils(context)) as T
        } else if (modelClass.isAssignableFrom(FishDetailViewModel::class.java)) {
            return FishDetailViewModel(context, Injection.provideStoryRepo()) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(pref,context, Injection.provideStoryRepo()) as T
        } else if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(pref, DialogUtils(context)) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                pref,
                context,
                Injection.provideStoryRepo(),
                Injection.provideMyStoryRepo(context)
            ) as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(pref,Injection.provideMyStoryRepo(context)) as T
        } else if (modelClass.isAssignableFrom(UploadViewModel::class.java)) {
            return UploadViewModel(pref, DialogUtils(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}