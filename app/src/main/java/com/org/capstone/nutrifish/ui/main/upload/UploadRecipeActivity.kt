package com.org.capstone.nutrifish.ui.main.upload

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.org.capstone.nutrifish.databinding.ActivityUploadRecipeBinding
import com.org.capstone.nutrifish.utils.ImageUtils
import com.org.capstone.nutrifish.utils.SettingPreferences
import com.org.capstone.nutrifish.utils.Utils
import com.org.capstone.nutrifish.utils.ViewModelFactory
import com.org.capstone.nutrifish.utils.dataStore
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UploadRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadRecipeBinding
    private lateinit var uploadViewModel: UploadViewModel

    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewModel()
        setProcess()


    }

    private fun setViewModel() {
        val pref = SettingPreferences.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(this, pref)
        uploadViewModel = ViewModelProvider(this, viewModelFactory)[UploadViewModel::class.java]

        uploadViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        uploadViewModel.moveActivity.observe(this) {
            if (it == true) {
                Utils().toHome(this)
                finish()
            }
        }
    }

    private fun showLoading(it: Boolean) {
        // Implement loading logic here
    }

    private fun setProcess() {
        // Show keyboard when clicking on description
        binding.editRecipeDescription.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showKeyboardAndScroll(binding.editRecipeDescription)
            }
        }

        binding.backButton.setOnClickListener{
            Utils().toHome(this)
            finish()
        }
        binding.ivImagePreview.setOnClickListener { startGallery() }

        uploadViewModel.getUser().observe(this) { user ->
            val token = "Bearer ${user.token}"

            binding.btUpload.setOnClickListener {
                val description = binding.editRecipeDescription
                val title = binding.editRecipeTitle
                description.error = if (description.text.toString().isEmpty()) {
                    "Masukan deskripsi"
                } else {
                    null
                }

                title.error = if (title.text.toString().isEmpty()) {
                    "Masukkan judul"
                } else {
                    null
                }

                if (description.error == null && title.error == null) {
                    val locationLat = null
                    val locationLon = null

                    uploadStory(
                        token,
                        title.text.toString(),
                        description.text.toString(),
                        locationLat,
                        locationLon
                    )
                }
            }
        }
    }

    private fun uploadStory(
        token: String,
        title: String,
        description: String,
        locationLat: Double?,
        locationLon: Double?
    ) {
        if (getFile != null) {
            val imageFile = ImageUtils().reduceFileImage(getFile as File)
            val requestImage = imageFile.asRequestBody("image/jpg".toMediaType())
            val descriptionRequest = description.toRequestBody("text/plain".toMediaType())
            val titleRequest = title.toRequestBody("text/plain".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImage
            )

            uploadViewModel.uploadStory(token, titleRequest, descriptionRequest, locationLat, locationLon, imageMultipart)
        } else {
            Toast.makeText(this, "Masukkan photo untuk di upload", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startGallery() {
        val intentGallery = Intent()
        intentGallery.action = ACTION_GET_CONTENT
        intentGallery.type = "image/*"
        val intentChooser = Intent.createChooser(intentGallery, "Pilih Gambar")
        launcherIntentGallery.launch(intentChooser)
    }

    private val launcherIntentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImage = result.data?.data as Uri
                selectedImage.let { uri ->
                    val myFile = ImageUtils().uriTofile(uri, this)
                    getFile = myFile
                    ImageUtils().rotateFile(myFile)
                    binding.ivImagePreview.setImageURI(uri)
                }
            }
        }

    private fun showKeyboardAndScroll(view: View) {
        view.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        binding.nestedScrollView.post {
            binding.nestedScrollView.smoothScrollTo(0, view.bottom)
        }
    }
}
