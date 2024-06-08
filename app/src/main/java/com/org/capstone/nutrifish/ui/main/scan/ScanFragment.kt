package com.org.capstone.nutrifish.ui.main.scan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.org.capstone.nutrifish.databinding.FragmentScanBinding
import com.org.capstone.nutrifish.helper.ImageClassifierHelper
import com.org.capstone.nutrifish.utils.ImageUtils
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File

class ScanFragment : Fragment() {
    private lateinit var binding: FragmentScanBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private lateinit var currentImagePath: String
    private var getFile: File? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanBinding.inflate(layoutInflater, container, false)

        setProcess()

        return binding.root
    }

    private fun setProcess() {
        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btCamera.setOnClickListener { startCamera() }
        binding.btGallery.setOnClickListener { startGallery() }
        binding.btAnalyze.setOnClickListener {
            if (getFile == null) {
                showToast("Please add an image that you want to analyze")
            } else {
                analyzeImage()
            }
        }
    }

    private fun analyzeImage() {
        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext().applicationContext,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    showToast("Error: $error")
                }

                override fun onResults(result: List<Classifications>?, inferenceTime: Long) {
                    result?.let { classifications ->
                        if (classifications.isNotEmpty() && classifications[0].categories.isNotEmpty()) {
                            val sortedCategories =
                                classifications[0].categories.sortedByDescending { it?.score }
                            val prediction = sortedCategories[0].label
                            val score = sortedCategories[0].score
                            val result: String = when (prediction.toString()) {
                                "0" -> "Bawal Putih"
                                "1" -> "Nila"
                                "2" -> "Pari"
                                "3" -> "Tongkol"
                                "4" -> "Tuna"
                                else -> "Unknown"
                            }
                            showToast("Prediction: $result\nScore: $score")
                        } else {
                            showToast("No classifications found.")
                        }
                    }
                }

            }
        )
        getFile?.let { imageClassifierHelper.classifyStaticImage(it.toUri()) }
    }

    private fun startGallery() {
        val intentGallery = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        val intentChooser = Intent.createChooser(intentGallery, "Choose an Image")
        launcherGalleryIntent.launch(intentChooser)
    }

    private val launcherGalleryIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImage = result.data?.data as Uri
                selectedImage.let { uri ->
                    val myFile = ImageUtils().uriTofile(uri, requireContext())
                    getFile = myFile
                    ImageUtils().rotateFile(myFile)
                    binding.ivImagePlaceholder.setImageURI(uri)
                }
            }
        }

    private fun startCamera() {
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        ImageUtils().createTemporaryFiles(requireContext().applicationContext).also {
            val photoUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                it
            )
            currentImagePath = it.absolutePath
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(intentCamera)

        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val newFile = File(currentImagePath)
            newFile.let { file ->
                getFile = file
                ImageUtils().rotateFile(file)
                binding.ivImagePlaceholder.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }


    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }


}