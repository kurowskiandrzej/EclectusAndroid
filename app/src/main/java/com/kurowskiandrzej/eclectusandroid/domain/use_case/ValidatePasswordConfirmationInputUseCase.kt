package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.R
import javax.inject.Inject

class ValidatePasswordConfirmationInputUseCase @Inject constructor() {
    operator fun invoke(
        passwordInput: String,
        passwordConfirmationInput: String
    ): Pair<Boolean, Int?> {
        return Pair(
            passwordInput == passwordConfirmationInput,
            R.string.password_does_not_match
        )
    }
}