package com.example.worldcinema.ui.screen.main.profile

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.worldcinema.databinding.DialogFragmentProfilePictureChoiceBinding


class ProfileAvatarChoiceDialog : DialogFragment() {

    private lateinit var binding: DialogFragmentProfilePictureChoiceBinding

    private var imageUri: Uri = Uri.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentProfilePictureChoiceBinding.inflate(inflater)
//        val file = File(activity?.filesDir, "picFromCamera")
//        imageUri = FileProvider.getUriForFile(
//            requireActivity(),
//            requireActivity().applicationContext.packageName + ".provider",
//            file
//        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileDialogCancelBnt.setOnClickListener {
            dialog?.dismiss()
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

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

    companion object {
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}