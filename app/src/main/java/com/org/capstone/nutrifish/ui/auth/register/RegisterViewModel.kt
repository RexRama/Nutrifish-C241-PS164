package com.org.capstone.nutrifish.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.utils.DialogUtils

class RegisterViewModel(private val dialogUtils: DialogUtils) : ViewModel() {

    private val mAuth = FirebaseAuth.getInstance()

    private val _moveActivity = MutableLiveData<Unit>()
    val moveActivity: LiveData<Unit> = _moveActivity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun userRegistration(user: UserModel, password: String) {
        _isLoading.value = true
        mAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isLoading.value = false
                    val userId = mAuth.currentUser?.uid ?: ""
                    saveToDatabase(userId, user)
                    dialogUtils.dialogSuccess(
                        "Registration Success",
                        "Your Account has been created. Please login to continue!"
                    ) {
                        _moveActivity.value = Unit
                    }
                } else {
                    _isLoading.value = false
                    dialogUtils.dialogError("Registration Failed", "Failed to register")
                }
            }
    }

    private fun saveToDatabase(userId: String, user: UserModel) {
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