package com.example.worldcinema.ui.auth.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentSignInBinding
import com.example.worldcinema.ui.main.MainActivity

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SignInViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            SignInViewModelFactory(requireContext()) // TODO(Подумать, мб передавать контекст приложения)
        )[SignInViewModel::class.java]
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

        binding.buttonEnter.setOnClickListener {
            viewModel.login(
                binding.emailInputEditText.text.toString(),
                binding.passwordInputEditText.text.toString()
            )
        }
        binding.buttonRegistration.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        // TODO(Перенести в лаунч скрин)
        viewModel.checkToken()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}