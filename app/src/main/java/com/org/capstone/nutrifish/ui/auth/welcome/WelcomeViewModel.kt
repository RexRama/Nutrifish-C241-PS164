package com.org.capstone.nutrifish.ui.auth.welcome


import android.net.Uri
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

//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//    private lateinit var googleSignInClient: GoogleSignInClient


    fun loginUser(user: LoginModel) {
        _isLoading.value = true
        val client = apiService.loginUser(user)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        isLogin(responseBody.loginResult.token)
                        saveUserToPreferences(
                            responseBody.loginResult.userID,
                            responseBody.loginResult.name,
                            responseBody.loginResult.username,
                            responseBody.loginResult.token,
                            false,
                            null
                        )
                        dialogUtils.dialogSuccess(
                            "Login Sukses",
                            "Login sukses, lanjut ke halaman utama!"
                        ) {
                            _moveActivity.value = Unit
                        }
                    } else {
                        dialogUtils.dialogError("Login Gagal", response.code().toString())
                    }

                } else if (response.code() == 400) {
                    dialogUtils.dialogError("Login Gagal", "Email atau Password anda salah")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                dialogUtils.dialogError("Login Failed", t.message)
            }

        })
    }

//    fun initGoogleLogin(activity: Activity) {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(BuildConfig.WEB_CLIENT_ID)
//            .requestEmail()
//            .requestProfile()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(activity, gso)
//    }
//
//    fun loginWithGoogle(resultLauncher: ActivityResultLauncher<Intent>) {
//        val signInIntent = googleSignInClient.signInIntent
//        resultLauncher.launch(signInIntent)
//    }
//
//    fun handleSignInResult(result: ActivityResult) {
//        if (result.resultCode == Activity.RESULT_OK) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            try {
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                Log.w(TAG, "Google sign in Failed", e)
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credentials = GoogleAuthProvider.getCredential(idToken, null)
//        _isLoading.value = true
//        auth.signInWithCredential(credentials)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//
//                    Log.d(TAG, "signInWithCredential:success")
//                    val user = auth.currentUser
//                    Log.d(TAG, idToken)
//                    user?.let {
//                        saveUserToPreferences(
//                            it.uid,
//                            it.displayName,
//                            it.email,
//                            idToken,
//                            true,
//                            it.photoUrl
//                        )
////                        saveUserToDatabase(it.uid, it.displayName, it.email)
//                    }
//                    dialogUtils.dialogSuccess(
//                        "Login Sukses",
//                        "Login sukses, lanjut ke halaman utama!"
//                    ) {
//                        _moveActivity.value = Unit
//                    }
//                    verifyGoogleTokenWithServer(idToken)
//                } else {
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                }
//                _isLoading.value = false
//            }
//    }
//
//
//
//    private fun verifyGoogleTokenWithServer(idToken: String) {
//        _isLoading.value = true
//        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), """{"idToken":"$idToken"}""")
//        val client = apiService.verifyGoogleToken(requestBody)
//        client.enqueue(object : Callback<VerifyGoogleTokenResponse> {
//            override fun onResponse(call: Call<VerifyGoogleTokenResponse>, response: Response<VerifyGoogleTokenResponse>) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null && !responseBody.error) {
//                        Log.d(TAG, "JWT Token: ${responseBody.token}")
//                        // You can save the JWT token or do other actions here
//                    } else {
//                        dialogUtils.dialogError("Verification Gagal", response.code().toString())
//                        Log.d(TAG, "JWT Token: ${response.message()}")
//                        Log.d(TAG, "JWT Token: ${response.code()}")
//                        Log.d(TAG, responseBody?.error.toString())
//                        responseBody?.let { Log.d(TAG, it.message) }
//                    }
//                } else {
//                    dialogUtils.dialogError("Verification Gagal", response.code().toString())
//                    Log.d(TAG, "JWT Token: ${response.message()}")
//                    Log.d(TAG, "JWT Token: ${response.code()}")
//                    Log.d(TAG, "JWT Token: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<VerifyGoogleTokenResponse>, t: Throwable) {
//                _isLoading.value = false
//                dialogUtils.dialogError("Verification Failed", t.message)
//
//            }
//        })
//    }
//
//    private fun saveUserToDatabase(uid: String, displayName: String?, email: String?) {
//        val userData = hashMapOf(
//            "name" to (displayName ?: ""),
//            "email" to (email ?: "")
//        )
//        FirebaseFirestore.getInstance().collection("users").document(uid)
//            .set(userData)
//            .addOnSuccessListener {
//                Log.d(TAG, "User data saved successfully")
//            }
//            .addOnFailureListener { e ->
//                Log.e(TAG, "Failed to save user data: ${e.message}")
//            }
//    }

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