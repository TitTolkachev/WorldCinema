package com.example.worldcinema.ui.main.compilation

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.main.compilation.model.SwipeRightCardModel
import com.example.worldcinema.ui.main.compilation.model.SwipeRightModel

class CompilationViewModel : ViewModel() {


    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------

    val modelStream: LiveData<SwipeRightModel>
        get() = stream

    private val stream = MutableLiveData<SwipeRightModel>()

    private var currentIndex = 0
    private val data = listOf(
        SwipeRightCardModel(backgroundColor = Color.parseColor("#E91E63")),
        SwipeRightCardModel(backgroundColor = Color.parseColor("#2196F3")),
        SwipeRightCardModel(backgroundColor = Color.parseColor("#F44336")),
        SwipeRightCardModel(backgroundColor = Color.parseColor("#9E9E9E"))
    )

    private val topCard
        get() = data[currentIndex % data.size]
    private val bottomCard
        get() = data[(currentIndex + 1) % data.size]

    init {
        updateStream()
    }

    fun swipe() {
        currentIndex++
        updateStream()
    }

    private fun updateStream() {
        stream.value = SwipeRightModel(
            top = topCard,
            bottom = bottomCard
        )
    }

    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
}