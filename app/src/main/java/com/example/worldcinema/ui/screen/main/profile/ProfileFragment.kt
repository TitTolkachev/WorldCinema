package com.example.worldcinema.ui.screen.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentProfileBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.dialog.showAlertDialog
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity
import com.example.worldcinema.ui.screen.main.profile.dialog.ProfileAvatarChoiceDialog

class ProfileFragment : Fragment(), ProfileAvatarChoiceDialog.IReloadListener, AlertDialog.IAlertDialogListener  {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(requireContext())
        )[ProfileViewModel::class.java]
        navController = findNavController()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.profileContent.visibility = View.GONE
                binding.progressBarProfile.visibility = View.VISIBLE
            } else {
                binding.profileContent.visibility = View.VISIBLE
                binding.progressBarProfile.visibility = View.GONE
                onDataLoaded()
            }
        }

        viewModel.showAlertDialog.observe(viewLifecycleOwner) {
            if(it) {
                showAlertDialog(viewModel.alertType.value ?: AlertType.DEFAULT)
            }
        }

        return binding.root
    }

    private fun onDataLoaded() {

        binding.buttonExit.setOnClickListener {
            viewModel.onExitBtnClick()
        }

        binding.buttonProfileMessenger.setOnClickListener {
            navController.navigate(R.id.action_navigation_profile_to_discussionsActivity)
        }

        binding.buttonChangeProfile.setOnClickListener {
            showProfileAvatarChoiceDialog()
        }

        viewModel.shouldExit.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(view?.context, SignInActivity::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_NEW_TASK
                )
                startActivity(intent)
                viewModel.onExited()
            }
        }

        viewModel.userProfile.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.textViewUserName.text = buildString {
                    append(it.firstName)
                    append(" ")
                    append(it.lastName)
                }
                binding.textViewUserEmail.text = it.email
                Glide.with(binding.imageViewAvatar).applyDefaultRequestOptions(
                    RequestOptions()
                        .error(R.drawable.default_avatar_icon)
                ).load(it.avatar).into(binding.imageViewAvatar)
            }
        }
    }

    private fun showProfileAvatarChoiceDialog() {
        val dialog = ProfileAvatarChoiceDialog()
        val bundle = Bundle()
        bundle.putString(
            getString(R.string.dialog_data_for_profile_avatar),
            viewModel.userProfile.value?.avatar
        )
        dialog.arguments = bundle
        dialog.show(childFragmentManager, "profile_avatar_choice")
    }

    override fun reload() {
        viewModel.loadProfileData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun alertDialogRetry() {
        viewModel.reload()
    }

    override fun onAlertDialogDismiss() {
        viewModel.alertShowed()
    }
}