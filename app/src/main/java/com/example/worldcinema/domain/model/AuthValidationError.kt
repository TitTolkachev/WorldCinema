package com.example.worldcinema.domain.model

enum class AuthValidationError{
    OK,

    EMPTY_FIRST_NAME,
    EMPTY_LAST_NAME,
    EMPTY_EMAIL,
    EMPTY_PASSWORD,
    EMPTY_REPEAT_PASSWORD,

    WRONG_EMAIL,
    WRONG_REPEAT_PASSWORD
}
