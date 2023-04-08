package com.example.worldcinema.ui.screen.collection.update

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.ActivityUpdateCollectionBinding
import com.example.worldcinema.ui.screen.collection.icon.IconCollectionActivity

class UpdateCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCollectionBinding
    private lateinit var viewModel: UpdateCollectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateCollectionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[UpdateCollectionViewModel::class.java]

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
        binding.buttonDeleteIcon.setOnClickListener {
            // TODO(Удалить коллекцию)
            finish()
        }

        setContentView(binding.root)
    }
}