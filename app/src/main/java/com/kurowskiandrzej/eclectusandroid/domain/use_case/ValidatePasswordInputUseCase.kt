package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.R
import com.kurowskiandrzej.eclectusandroid.common.Constants
import com.kurowskiandrzej.eclectusandroid.domain.validation.InputTextValidation
import javax.inject.Inject

class ValidatePasswordInputUseCase @Inject constructor() {
    private val validation = InputTextValidation.Builder()
        .setMinimumLength(Constants.PASSWORD_MINIMUM_LENGTH)
        .setMinimumLengthFailMessageResource(R.string.password_minimum_length_message)
        .build()

    operator fun invoke(passwordInput: String): Pair<Boolean, Int?> {
        return validation.validate(passwordInput)
    }
}