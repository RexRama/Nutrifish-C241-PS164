package com.org.capstone.nutrifish.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.org.capstone.nutrifish.data.remote.api.ApiConfig
import com.org.capstone.nutrifish.data.remote.model.RegisterModel
import com.org.capstone.nutrifish.data.remote.response.RegisterResponse
import com.org.capstone.nutrifish.utils.DialogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val dialogUtils: DialogUtils) : ViewModel() {
    private val apiService = ApiConfig.apiService()


    private val _moveActivity = MutableLiveData<Unit>()
    val moveActivity: LiveData<Unit> = _moveActivity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun userRegistration(user: RegisterModel) {
        _isLoading.value = true
        val client = apiService.registerUser(user)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        dialogUtils.dialogSuccess(
                            "Registrasi Berhasil!",
                            "Akun anda berhasil dibuat, login untuk melanjutkan!"
                        ) {
                            _moveActivity.value = Unit
                        }
                    } else {
                        dialogUtils.dialogError("Registrasi Gagal", response.message())
                        Log.d(TAG, "Registration Successful")
                    }
                } else if (response.code() == 400) {
                    dialogUtils.dialogError("Registrasi Gagal", "Email atau Username sudah terdaftar")
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                dialogUtils.dialogError("Registrasi Gagal", t.message)
                Log.e(TAG, t.message.toString())
            }
        })
    }


    companion object {
        private const val TAG = "RegisterViewModel"
    }
}