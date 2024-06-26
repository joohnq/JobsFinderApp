package com.joohnq.jobsfinderapp.util

sealed class PasswordValidatorException(message: String): Throwable(message) {
    data class PasswordIsEmpty(override val message: String = "Password is empty") :
        PasswordValidatorException(message)

    data class PasswordIsTooShort(override val message: String = "Password is short, minimum is 6 characters") :
        PasswordValidatorException(message)
}

class PasswordValidator {
    operator fun invoke(password: String): Boolean {
        if (password.isEmpty()) throw PasswordValidatorException.PasswordIsEmpty()
        if (password.length < 6) throw PasswordValidatorException.PasswordIsTooShort()
        return true
    }
}