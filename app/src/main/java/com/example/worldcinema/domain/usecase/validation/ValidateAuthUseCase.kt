package com.example.worldcinema.domain.usecase.validation

import com.example.worldcinema.domain.model.AuthValidationError

class ValidateAuthUseCase {

    fun execute(
        firstName: String? = null,
        lastName: String? = null,
        email: String,
        password: String,
        passwordRepeat: String? = null
    ): AuthValidationError {

        if (firstName?.isBlank() == true)
            return AuthValidationError.EMPTY_FIRST_NAME

        if (lastName?.isBlank() == true)
            return AuthValidationError.EMPTY_LAST_NAME

        if (email.isBlank())
            return AuthValidationError.EMPTY_EMAIL

        if (password.isBlank())
            return AuthValidationError.EMPTY_PASSWORD

        if (passwordRepeat?.isBlank() == true)
            return AuthValidationError.EMPTY_REPEAT_PASSWORD

        if (!email.isEmailValid())
            return AuthValidationError.WRONG_EMAIL

        if (passwordRepeat != null && password != passwordRepeat)
            return AuthValidationError.WRONG_REPEAT_PASSWORD

        return AuthValidationError.OK
    }

    private fun String.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}