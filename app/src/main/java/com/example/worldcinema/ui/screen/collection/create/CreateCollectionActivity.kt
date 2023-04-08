package com.example.worldcinema.ui.screen.collection.create

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.ActivityCreateCollectionBinding
import com.example.worldcinema.ui.screen.collection.icon.IconCollectionActivity

class CreateCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCollectionBinding
    private lateinit var viewModel: CreateCollectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCollectionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CreateCollectionViewModel::class.java]

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
        }
        binding.buttonChooseIcon.setOnClickListener {
            val intent =
                Intent(applicationContext, IconCollectionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(intent)
        }
        binding.buttonSaveIcon.setOnClickListener {
            // TODO(Сохранить изменения коллекции)
            finish()
        }

        setContentView(binding.root)
    }
}