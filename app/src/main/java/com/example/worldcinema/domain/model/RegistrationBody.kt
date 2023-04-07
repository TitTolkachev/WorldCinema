package com.example.worldcinema.domain.model

data class RegistrationBody(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)
