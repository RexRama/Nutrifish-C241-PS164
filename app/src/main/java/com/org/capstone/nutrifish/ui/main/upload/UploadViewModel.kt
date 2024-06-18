package com.org.capstone.nutrifish.ui.main.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.org.capstone.nutrifish.data.remote.api.ApiConfig
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.data.remote.response.UploadStoryResponse
import com.org.capstone.nutrifish.utils.DialogUtils
import com.org.capstone.nutrifish.utils.SettingPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadViewModel(private val pref: SettingPreferences, val dialogUtils: DialogUtils) : ViewModel() {

    private val apiService = ApiConfig.apiService()

    private val _moveActivity = MutableLiveData<Boolean>()
    val moveActivity: LiveData<Boolean> = _moveActivity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadStory(
        token: String,
        storyTitle: RequestBody,
        storyDescription: RequestBody,
        lat: Double?,
        lon: Double?,
        imageMultipart: MultipartBody.Part
    ) {
        _isLoading.value = true
        val client =
            apiService.uploadStory(token, imageMultipart, storyTitle, storyDescription, lat, lon)
        client.enqueue(object : Callback<UploadStoryResponse> {
            override fun onResponse(
                call: Call<UploadStoryResponse>,
                response: Response<UploadStoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        dialogUtils.dialogSuccess(
                            "Upload Berhasil!",
                            "Cerita anda berhasil ter-Upload!"
                        ) {
                            _moveActivity.value = true
                        }
                    }
                } else {
                    dialogUtils.dialogError("Upload Gagal", response.message())
                    Log.d("Upload", "Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UploadStoryResponse>, t: Throwable) {
                _isLoading.value = false
                dialogUtils.dialogError("Upload Failed", t.message)
                Log.e("Upload error:", t.message.toString())
            }

        })
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}