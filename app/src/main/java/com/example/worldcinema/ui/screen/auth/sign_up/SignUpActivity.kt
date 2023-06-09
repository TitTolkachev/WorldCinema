package com.example.worldcinema.ui.screen.auth.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.ActivitySignUpBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.dialog.showAlertDialog
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity
import com.example.worldcinema.ui.screen.main.MainActivity

class SignUpActivity : AppCompatActivity(), AlertDialog.IAlertDialogExitListener,
    AlertDialog.IAlertDialogListener {

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

        viewModel.showAlertDialog.observe(this) {
            if (it) {
                showAlertDialog(viewModel.alertType.value ?: AlertType.DEFAULT)
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

    override fun alertDialogExit() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }

    override fun alertDialogRetry() {
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

    override fun onAlertDialogDismiss() {}
}