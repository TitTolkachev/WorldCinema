package com.example.worldcinema.domain.model

data class User(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String?
)