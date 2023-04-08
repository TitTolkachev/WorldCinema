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
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentProfileBinding
import com.example.worldcinema.ui.screen.auth.AuthActivity

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

        viewModel.shouldExit.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(view?.context, AuthActivity::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_NEW_TASK
                )
                startActivity(intent)
                viewModel.onExited()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}