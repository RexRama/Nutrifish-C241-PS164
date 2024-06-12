package com.org.capstone.nutrifish.ui.main

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.org.capstone.nutrifish.BuildConfig
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.utils.SettingPreferences
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainViewModel(private val pref: SettingPreferences?, context: Context) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient

    private val _moveActivity = MutableLiveData<Unit>()
    val moveActivity: LiveData<Unit> = _moveActivity

    init {
        initGoogleSignInClient(context)
    }

    private fun initGoogleSignInClient(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.WEB_CLIENT_ID)
            .requestEmail()
            .requestProfile()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun setToken(token: String) {

    }

    fun getUser(): LiveData<UserModel>? {
        return pref?.getUser()?.asLiveData()
    }

    fun logout(activity: Activity) {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener(activity) {
            viewModelScope.launch {
                pref?.logout()
                _moveActivity.value = Unit
                Log.d(TAG, "Logout Success")

            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}