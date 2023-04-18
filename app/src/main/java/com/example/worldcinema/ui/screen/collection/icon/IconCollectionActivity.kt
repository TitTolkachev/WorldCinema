package com.example.worldcinema.ui.screen.collection.icon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.get
import com.example.worldcinema.R
import com.example.worldcinema.databinding.ActivityIconCollectionBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity

class IconCollectionActivity : AppCompatActivity(), AlertDialog.IAlertDialogExitListener {

    private lateinit var binding: ActivityIconCollectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIconCollectionBinding.inflate(layoutInflater)

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
        }

        inflateIcons()

        setContentView(binding.root)
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

    private fun inflateIcons() {

        val icons = listOf(
            R.drawable.ic_1,
            R.drawable.ic_2,
            R.drawable.ic_3,
            R.drawable.ic_4,
            R.drawable.ic_5,
            R.drawable.ic_6,
            R.drawable.ic_7,
            R.drawable.ic_8,
            R.drawable.ic_9,
            R.drawable.ic_10,
            R.drawable.ic_11,
            R.drawable.ic_12,
            R.drawable.ic_13,
            R.drawable.ic_14,
            R.drawable.ic_15,
            R.drawable.ic_16,
            R.drawable.ic_17,
            R.drawable.ic_18,
            R.drawable.ic_19,
            R.drawable.ic_20,
            R.drawable.ic_21,
            R.drawable.ic_22,
            R.drawable.ic_23,
            R.drawable.ic_24,
            R.drawable.ic_25,
            R.drawable.ic_26,
            R.drawable.ic_27,
            R.drawable.ic_28,
            R.drawable.ic_29,
            R.drawable.ic_30,
            R.drawable.ic_31,
            R.drawable.ic_32,
            R.drawable.ic_33,
            R.drawable.ic_34,
            R.drawable.ic_35,
            R.drawable.ic_36,
        )
        icons.forEachIndexed { index, i ->
            when (index % 4) {
                0 -> {
                    layoutInflater.inflate(R.layout.collection_icon, binding.FlexboxIcons1)
                    val view = binding.FlexboxIcons1[binding.FlexboxIcons1.childCount - 1]
                    view.id = View.generateViewId()
                    (view as ImageView).setImageResource(i)
                    view.setOnClickListener {
                        onItemClick(index)
                    }
                }
                1 -> {
                    layoutInflater.inflate(R.layout.collection_icon, binding.FlexboxIcons2)
                    val view = binding.FlexboxIcons2[binding.FlexboxIcons2.childCount - 1]
                    view.id = View.generateViewId()
                    (view as ImageView).setImageResource(i)
                    view.setOnClickListener {
                        onItemClick(index)
                    }
                }
                2 -> {
                    layoutInflater.inflate(R.layout.collection_icon, binding.FlexboxIcons3)
                    val view = binding.FlexboxIcons3[binding.FlexboxIcons3.childCount - 1]
                    view.id = View.generateViewId()
                    (view as ImageView).setImageResource(i)
                    view.setOnClickListener {
                        onItemClick(index)
                    }
                }
                3 -> {
                    layoutInflater.inflate(R.layout.collection_icon, binding.FlexboxIcons4)
                    val view = binding.FlexboxIcons4[binding.FlexboxIcons4.childCount - 1]
                    view.id = View.generateViewId()
                    (view as ImageView).setImageResource(i)
                    view.setOnClickListener {
                        onItemClick(index)
                    }
                }
            }
        }
    }

    private fun onItemClick(index: Int) {
        val data = Intent()
        data.putExtra(getString(R.string.intent_data_for_collection_icon), index)
        setResult(RESULT_OK, data)
        finish()
    }
}