package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.R
import com.kurowskiandrzej.eclectusandroid.domain.validation.InputTextValidation
import javax.inject.Inject

class ValidateEmailInputUseCase @Inject constructor() {
    private val validation = InputTextValidation.Builder()
        .setRegexPattern(Regex("""^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"""))
        .setRegexPatternFailMessageResource(R.string.invalid_email_message)
        .build()

    operator fun invoke(email: String): Pair<Boolean, Int?> {
        return validation.validate(email)
    }
}