package com.example.worldcinema.ui.discussions.discussions.model

data class Discussion(
    val chatId: String,
    val movieId: String,
    val preview: String,
    val movieName: String,
    val authorName: String,
    val text:String
)
