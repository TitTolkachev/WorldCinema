package com.example.worldcinema.ui.screen.collection.create

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.R
import com.example.worldcinema.databinding.ActivityCreateCollectionBinding
import com.example.worldcinema.ui.screen.collection.icon.IconCollectionActivity

class CreateCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCollectionBinding
    private lateinit var viewModel: CreateCollectionViewModel

    private val intentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedIndex =
                    result.data?.getIntExtra(getString(R.string.intent_data_for_collection_icon), 0)
                viewModel.setIconIndex(selectedIndex ?: viewModel.iconIndex.value ?: 0)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCollectionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            CreateCollectionViewModelFactory(
                this,
                getString(R.string.collection_favourites_default_name)
            )
        )[CreateCollectionViewModel::class.java]

        viewModel.exit.observe(this) {
            if (it)
                finish()
        }

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
        }

        binding.buttonChooseIcon.setOnClickListener {
            intentLauncher.launch(Intent(this, IconCollectionActivity::class.java))
        }

        binding.buttonSaveIcon.setOnClickListener {
            viewModel.createCollection(binding.collectionNameInput.text.toString())
        }

        viewModel.iconIndex.observe(this) {
            setIcon(it)
        }

        setContentView(binding.root)
    }

    private fun setIcon(index: Int) {
        when (index) {
            0 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_1)
            1 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_2)
            2 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_3)
            3 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_4)
            4 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_5)
            5 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_6)
            6 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_7)
            7 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_8)
            8 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_9)
            9 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_10)
            10 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_11)
            11 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_12)
            12 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_13)
            13 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_14)
            14 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_15)
            15 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_16)
            16 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_17)
            17 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_18)
            18 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_19)
            19 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_20)
            20 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_21)
            21 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_22)
            22 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_23)
            23 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_24)
            24 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_25)
            25 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_26)
            26 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_27)
            27 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_28)
            28 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_29)
            29 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_30)
            30 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_31)
            31 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_32)
            32 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_33)
            33 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_34)
            34 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_35)
            35 -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_36)
            else -> binding.imageViewCollectionIcon.setImageResource(R.drawable.ic_1)
        }
    }
}