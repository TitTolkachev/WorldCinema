package com.example.worldcinema.ui.screen.main.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.worldcinema.databinding.DialogFragmentProfilePictureChoiceBinding

class ProfileAvatarChoiceDialog : DialogFragment() {

    private lateinit var binding: DialogFragmentProfilePictureChoiceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentProfilePictureChoiceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileDialogCancelBnt.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}