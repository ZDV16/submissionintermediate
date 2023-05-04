package com.example.submissionintermediate.addStory

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionintermediate.R
import com.example.submissionintermediate.databinding.ActivityAddStoryBinding
import com.example.submissionintermediate.main.MainActivity
import com.example.submissionintermediate.settings.ApiResult
import com.example.submissionintermediate.settings.UserPreferences
import com.example.submissionintermediate.settings.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private var getFile: File? = null

    private lateinit var factory: ViewModelFactory
    private lateinit var addViewModel: AddStoryViewModel

    var desc =""


    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        addViewModel = ViewModelProvider(this, factory)[AddStoryViewModel::class.java]

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        uploadStory()

        binding.btnCamera.setOnClickListener { startCameraX() }
        binding.btnGallery.setOnClickListener { startGallery() }

        supportActionBar?.hide()
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile

            myFile.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.ivPreview.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@AddStoryActivity)

            getFile = myFile
            binding.ivPreview.setImageURI(selectedImg)
        }
    }

    private fun uploadStory() {

        binding.btnUpload.setOnClickListener {
            if (binding.tvDescription.text.isEmpty()) {
                Toast.makeText(this, R.string.errorDesc, Toast.LENGTH_SHORT).show()
            } else {
                desc = binding.tvDescription.text.toString()
            }
            if (getFile == null) {
                Toast.makeText(this, R.string.uploadNo, Toast.LENGTH_SHORT).show()
            }

            addViewModel.getUser().observe(this) { it ->
                val token = "Bearer ${it.token}"
                val file = reduceFileImage(getFile as File)
                val desc = "${binding.tvDescription.text}".toRequestBody("text/plain".toMediaTypeOrNull())
                val reqImgFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo", file.name, reqImgFile
                )
                addViewModel.getUser().observe(this) {
                    addViewModel.uploadFile(token, imageMultipart, desc)
                        .observe(this@AddStoryActivity) {
                            when (it) {
                                is ApiResult.Success-> {
                                    showLoading(false)
                                    startActivity(Intent(this, MainActivity::class.java))
                                    Toast.makeText(this, R.string.uploadYes, Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                is ApiResult.Loading -> {showLoading(true)}
                                is ApiResult.Error -> {
                                    showLoading(false)
                                    Toast.makeText(this, R.string.uploadNo, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                }
            }
            }
        }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar5.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}