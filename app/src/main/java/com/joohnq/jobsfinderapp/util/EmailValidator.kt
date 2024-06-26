package com.joohnq.jobsfinderapp.util

import android.util.Patterns

sealed class EmailValidatorException(message: String) : Throwable(message) {
    data class EmailIsEmpty(override val message: String = "Email is empty") :
        PasswordValidatorException(message)

    data class EmailIsInvalid(override val message: String = "Email is invalid") :
        PasswordValidatorException(message)
}

class EmailValidator {
    operator fun invoke(email: String): Boolean {
        if (email.isEmpty()) throw EmailValidatorException.EmailIsEmpty()
        if (Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
                .not()
        ) throw EmailValidatorException.EmailIsInvalid()
        return true
    }
}