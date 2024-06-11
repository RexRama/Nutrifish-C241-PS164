package com.org.capstone.nutrifish.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.org.capstone.nutrifish.data.remote.api.ApiConfig
import com.org.capstone.nutrifish.data.remote.model.RegisterModel
import com.org.capstone.nutrifish.data.remote.response.RegisterResponse
import com.org.capstone.nutrifish.utils.DialogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val dialogUtils: DialogUtils) : ViewModel() {
    private val apiService = ApiConfig.apiService()

    private val mAuth = FirebaseAuth.getInstance()

    private val _moveActivity = MutableLiveData<Unit>()
    val moveActivity: LiveData<Unit> = _moveActivity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    fun userRegistration(user: RegisterModel) {
//        _isLoading.value = true
//        mAuth.createUserWithEmailAndPassword(user.email, user.password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _isLoading.value = false
//                    val userId = mAuth.currentUser?.uid ?: ""
//                    saveToDatabase(userId, user)
//                    dialogUtils.dialogSuccess(
//                        "Registration Success",
//                        "Your Account has been created. Please login to continue!"
//                    ) {
//                        _moveActivity.value = Unit
//                    }
//                } else {
//                    _isLoading.value = false
//                    dialogUtils.dialogError("Registration Failed", "Failed to register")
//                }
//            }
//    }

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
                            "Account Successfully Created",
                            "Your Account has been created. Please login to continue!"
                        ) {
                            _moveActivity.value = Unit
                        }
                    } else {
                        dialogUtils.dialogError("Registration Failed", response.message())
                    }
                } else if (response.code() == 400) {
                    dialogUtils.dialogError("Registration Failed", "Email or Username already taken!")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                dialogUtils.dialogError("Registration Failed", t.message)
            }
        })
    }

    private fun saveToDatabase(userId: String, user: RegisterModel) {
        val userData = hashMapOf(
            "name" to user.name,
            "username" to user.username,
            "email" to user.email
        )
        FirebaseFirestore.getInstance().collection("users").document(userId)
            .set(userData)
            .addOnSuccessListener {
                Log.d(TAG, "User data saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to save user data: ${e.message}", e)
            }
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}