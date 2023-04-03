package com.example.worldcinema.ui.movie.episode

import android.util.Log
import androidx.lifecycle.ViewModel

class EpisodeViewModel : ViewModel() {



    fun saveVideoPosition(contentPosition: Long, contentDuration: Long) {
        Log.d("Position", contentPosition.toString())
        Log.d("Duration", contentDuration.toString())
    }

}