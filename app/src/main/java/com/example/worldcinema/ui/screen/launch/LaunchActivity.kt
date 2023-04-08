package com.example.worldcinema.ui.screen.launch

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.R
import com.example.worldcinema.ui.screen.auth.AuthActivity
import com.example.worldcinema.ui.screen.main.MainActivity

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_WorldCinema)
        super.onCreate(savedInstanceState)

        val viewModel =
            ViewModelProvider(this, LaunchViewModelFactory(this))[LaunchViewModel::class.java]

        viewModel.navigateToMainScreen.observe(this) {
            if(it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                viewModel.navigatedToMainScreen()
                finish()
            }
        }

        viewModel.navigateToSignInScreen.observe(this) {
            if(it) {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                viewModel.navigatedToSignInScreen()
                finish()
            }
        }

        viewModel.checkToken()
    }
}