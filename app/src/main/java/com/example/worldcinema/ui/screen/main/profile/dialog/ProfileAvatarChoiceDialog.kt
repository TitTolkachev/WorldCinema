package com.example.worldcinema.ui.screen.main.profile.dialog

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.worldcinema.R
import com.example.worldcinema.databinding.DialogFragmentProfilePictureChoiceBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.dialog.showAlertDialog
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileAvatarChoiceDialog : DialogFragment(), AlertDialog.IAlertDialogListener {

    private lateinit var binding: DialogFragmentProfilePictureChoiceBinding
    private lateinit var viewModel: ProfileAvatarDialogViewModel

    private lateinit var imageUri: Uri
    private var newImageLoaded = false

    interface IReloadListener {
        fun reload()
    }

    private var reloadListener: IReloadListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentProfilePictureChoiceBinding.inflate(inflater)
        viewModel = ViewModelProvider(
            this,
            ProfileAvatarDialogViewModelFactory(requireContext())
        )[ProfileAvatarDialogViewModel::class.java]

        Glide.with(binding.imageViewAvatarChoice).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(android.R.color.transparent)
                .error(R.drawable.default_avatar_icon)
        )
            .load(arguments?.getString(getString(R.string.dialog_data_for_profile_avatar)))
            .into(binding.imageViewAvatarChoice)

        val file = createImageFile()
        imageUri = FileProvider.getUriForFile(
            requireActivity(),
            "com.example.worldcinema.fileprovider",
            file
        )

        viewModel.showAlertDialog.observe(viewLifecycleOwner) {
            if(it) {
                showAlertDialog(viewModel.alertType.value ?: AlertType.DEFAULT)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileDialogCancelBtn.setOnClickListener {
            dialog?.dismiss()
        }
        binding.profileDialogSaveBtn.setOnClickListener {
            if (newImageLoaded)
                viewModel.sendAvatarImage(
                    getResizedBitmap(
                        getCapturedImage(
                            imageUri
                        )
                    )
                )
            else
                dialog?.dismiss()
        }

        binding.profileDialogCameraBtn.setOnClickListener {
            when (ContextCompat.checkSelfPermission(requireContext(), CAMERA_PERMISSION)) {
                PackageManager.PERMISSION_GRANTED -> {
                    val file = createImageFile()
                    imageUri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.example.worldcinema.fileprovider",
                        file
                    )
                    getCameraImageActivityResultLauncher.launch(imageUri)
                }
                else -> requestPermissionLauncher.launch(CAMERA_PERMISSION)
            }
        }
        binding.profileDialogGalleryBtn.setOnClickListener {
            getGalleryImageActivityResultLauncher.launch("image/*")
        }

        viewModel.closeDialog.observe(viewLifecycleOwner) {
            if (it) {
                dialog?.dismiss()
            }
        }
        viewModel.remoteImageChanged.observe(viewLifecycleOwner) {
            if (it) {
                reloadListener?.reload()
                viewModel.profileReloaded()
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it)
                binding.progressBarProfileDialog.visibility = View.VISIBLE
            else
                binding.progressBarProfileDialog.visibility = View.INVISIBLE
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            reloadListener = parentFragment as IReloadListener?
        } catch (e: ClassCastException) {
            Log.e("Context not found in Avatar Dialog Fragment", e.message.toString())
        }
    }

    private val getCameraImageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                newImageLoaded = true
                binding.imageViewAvatarChoice.setImageBitmap(getCapturedImage(imageUri))
            }
        }

    private val getGalleryImageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                newImageLoaded = true
                binding.imageViewAvatarChoice.setImageBitmap(
                    getResizedBitmap(
                        getCapturedImage(
                            imageUri
                        )
                    )
                )
            }
        }

    private fun getCapturedImage(selectedImage: Uri): Bitmap {
        return when {
            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                requireActivity().contentResolver,
                selectedImage
            )
            else -> {
                val source =
                    ImageDecoder.createSource(requireActivity().contentResolver, selectedImage)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    private fun getResizedBitmap(bm: Bitmap): Bitmap? {
        val maxHeight = 1000
        val maxWidth = 1000
        val scale: Float =
            (maxHeight.toFloat() / bm.width).coerceAtMost(maxWidth.toFloat() / bm.height)

        val matrix = Matrix()
        matrix.postScale(scale, scale)
        return Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getCameraImageActivityResultLauncher.launch(imageUri)
            } else {
                viewModel.showAlert(AlertType.NO_PERMISSIONS)
            }
        }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat.getDateTimeInstance().format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    }

    override fun alertDialogRetry() {
        dialog?.dismiss()
    }

    override fun onAlertDialogDismiss() {
        viewModel.alertShowed()
    }

    companion object {
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}