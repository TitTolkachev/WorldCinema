package com.example.worldcinema.ui.screen.auth.sign_up

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentSignUpBinding
import com.example.worldcinema.ui.screen.main.MainActivity

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SignUpViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            SignUpViewModelFactory(requireContext())
        )[SignUpViewModel::class.java]
        navController = findNavController()

        viewModel.navigateToMainScreen.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(view?.context, MainActivity::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_NEW_TASK
                )
                startActivity(intent)
                viewModel.navigatedToMainScreen()
            }
        }

        binding.buttonRegister.setOnClickListener {
            with(binding) {
                viewModel.onRegisterBtnClick(
                    nameInputEditText.text.toString(),
                    surnameInputEditText.text.toString(),
                    emailInputEditText.text.toString(),
                    passwordInputEditText.text.toString(),
                    passwordRepeatInputEditText.text.toString()
                )
            }
        }

        binding.buttonHaveAccount.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}