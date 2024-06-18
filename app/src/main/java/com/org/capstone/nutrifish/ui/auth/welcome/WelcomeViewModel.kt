package com.org.capstone.nutrifish.ui.auth.welcome


import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.org.capstone.nutrifish.data.remote.api.ApiConfig
import com.org.capstone.nutrifish.data.remote.model.LoginModel
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.data.remote.response.LoginResponse
import com.org.capstone.nutrifish.utils.DialogUtils
import com.org.capstone.nutrifish.utils.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeViewModel(
    private val pref: SettingPreferences,
    private val dialogUtils: DialogUtils,
) :
    ViewModel() {
    private val apiService = ApiConfig.apiService()

    private val _moveActivity = MutableLiveData<Unit>()
    val moveActivity: LiveData<Unit> = _moveActivity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun capitalizeFirstWord(input: String): String {
        if (input.isBlank()) return input

        val words = input.split(" ").toMutableList()
        words[0] = words[0].replaceFirstChar { it.uppercase() }

        return words.joinToString(" ")
    }

    fun loginUser(user: LoginModel) {
        _isLoading.value = true
        val client = apiService.loginUser(user)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        val capitalizedUsername = capitalizeFirstWord(responseBody.loginResult.username)
                        isLogin(responseBody.loginResult.token)
                        saveUserToPreferences(
                            responseBody.loginResult.userID,
                            responseBody.loginResult.name,
                            capitalizedUsername,
                            responseBody.loginResult.token,
                            false,
                            null
                        )
                        Log.d(TAG, response.message())
                        dialogUtils.dialogSuccess(
                            "Login Sukses",
                            "Login sukses, lanjut ke halaman utama!"
                        ) {
                            _moveActivity.value = Unit
                        }
                    } else {
                        Log.e(TAG, response.message())
                        dialogUtils.dialogError("Login Gagal", response.code().toString())
                    }

                } else if (response.code() == 400) {
                    Log.e(TAG, response.message())
                    dialogUtils.dialogError("Login Gagal", "Email atau Password anda salah")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                dialogUtils.dialogError("Login Failed", t.message)
            }

        })
    }


    private fun saveUserToPreferences(
        uid: String,
        displayName: String?,
        email: String?,
        token: String,
        isGoogle : Boolean,
        photo: Uri?
    ) {
        viewModelScope.launch {
            pref.setUser(
                UserModel(
                    uid,
                    displayName ?: "",
                    email ?: "",
                    email ?: "",
                    "",
                    true,
                    token,
                    isGoogle,
                    photo.toString()
                )
            )
        }
    }


    private fun isLogin(token: String) {
        viewModelScope.launch {
            pref.login(token)
        }
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    companion object {
        private const val TAG = "WelcomeViewModel"
    }
}