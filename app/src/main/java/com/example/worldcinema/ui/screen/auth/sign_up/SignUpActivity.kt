package com.example.worldcinema.ui.screen.auth.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.ActivitySignUpBinding
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity
import com.example.worldcinema.ui.screen.main.MainActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            SignUpViewModelFactory(this)
        )[SignUpViewModel::class.java]

        viewModel.navigateToMainScreen.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
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
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        super.onResume()
    }
}