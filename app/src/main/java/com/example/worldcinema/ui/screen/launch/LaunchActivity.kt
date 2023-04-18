package com.example.worldcinema.ui.screen.launch

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.R
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity
import com.example.worldcinema.ui.screen.auth.sign_up.SignUpActivity
import com.example.worldcinema.ui.screen.main.MainActivity

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_WorldCinema)
        super.onCreate(savedInstanceState)

        val viewModel =
            ViewModelProvider(this, LaunchViewModelFactory(this))[LaunchViewModel::class.java]

        if (viewModel.isEnterFirst()) {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            viewModel.navigateToMainScreen.observe(this) {
                if (it) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            viewModel.navigateToSignInScreen.observe(this) {
                if (it) {
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            viewModel.checkToken()
        }
    }
}