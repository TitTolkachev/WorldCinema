package com.example.worldcinema.ui.screen.auth.sign_in

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.ActivitySignInBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.dialog.showAlertDialog
import com.example.worldcinema.ui.screen.auth.sign_up.SignUpActivity
import com.example.worldcinema.ui.screen.main.MainActivity

class SignInActivity : AppCompatActivity(), AlertDialog.IAlertDialogExitListener,
    AlertDialog.IAlertDialogListener {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            SignInViewModelFactory(this)
        )[SignInViewModel::class.java]

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

        binding.buttonEnter.setOnClickListener {
            viewModel.login(
                binding.emailInputEditText.text.toString(),
                binding.passwordInputEditText.text.toString()
            )
        }
        binding.buttonRegistration.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
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
        finish()
    }

    override fun alertDialogRetry() {
        viewModel.login(
            binding.emailInputEditText.text.toString(),
            binding.passwordInputEditText.text.toString()
        )
    }

    override fun onAlertDialogDismiss() {}
}