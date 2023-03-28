package com.example.worldcinema.ui.auth.sign_in

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: SignInViewModel

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        navController = findNavController()

        binding.buttonEnter.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_homeFragment)
        }
        binding.buttonRegistration.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}