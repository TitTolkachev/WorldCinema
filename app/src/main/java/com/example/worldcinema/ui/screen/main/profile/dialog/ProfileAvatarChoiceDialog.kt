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
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileAvatarChoiceDialog : DialogFragment() {

    private lateinit var binding: DialogFragmentProfilePictureChoiceBinding
    private lateinit var viewModel: ProfileAvatarDialogViewModel

    private var imageUri: Uri = Uri.EMPTY

    interface IReloadListener {
        fun reload()
    }

    var reloadListener: IReloadListener? = null

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileDialogCancelBnt.setOnClickListener {
            dialog?.dismiss()
        }
        binding.profileDialogSaveBnt.setOnClickListener {
            viewModel.sendAvatarImage(
                getResizedBitmap(
                    getCapturedImage(
                        imageUri
                    )
                )
            )
        }

        binding.profileDialogCameraBnt.setOnClickListener {
            when (ContextCompat.checkSelfPermission(requireContext(), CAMERA_PERMISSION)) {
                PackageManager.PERMISSION_GRANTED -> getCameraImageActivityResultLauncher.launch(
                    imageUri
                )
                else -> requestPermissionLauncher.launch(CAMERA_PERMISSION)
            }
        }
        binding.profileDialogGalleryBnt.setOnClickListener {
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
                binding.imageViewAvatarChoice.setImageBitmap(getCapturedImage(imageUri))
            }
        }

    private val getGalleryImageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
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
                // TODO(Ошибка, не дал права)
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

    companion object {
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}