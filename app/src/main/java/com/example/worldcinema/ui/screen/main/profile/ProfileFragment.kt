package com.example.worldcinema.ui.screen.main.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentProfileBinding
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity

class ProfileFragment : Fragment() {

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
                Glide.with(binding.imageViewAvatar).load(it.avatar).into(binding.imageViewAvatar)
            }
        }

        return binding.root
    }

    private fun showProfileAvatarChoiceDialog() {
        val dialog = ProfileAvatarChoiceDialog()
        dialog.show(childFragmentManager, "profile_avatar_choice")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}