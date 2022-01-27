package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.common.Status
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.RegisterResponseDto
import javax.inject.Inject

class ButtonRegisterRequestEnableStateUseCase @Inject constructor() {
    operator fun invoke(
        registerRequestResponse: Resource<Pair<RegisterResponseDto, String>>?,
        emailInputValidationResult: Boolean?,
        passwordInputValidationResult: Boolean?,
        passwordConfirmationInputValidationResult: Boolean?
    ): Boolean {
        if (!checkValidationResults(
                emailInputValidationResult,
                passwordInputValidationResult,
                passwordConfirmationInputValidationResult
            )
        ) return false

        registerRequestResponse?.let { response ->
            if (response.status == Status.LOADING) return false
        }

        return true
    }

    private fun checkValidationResults(
        vararg validationResults: Boolean?
    ): Boolean {
        validationResults.forEach { validationResult ->
            validationResult?.let {
                if (!it) return false
            } ?: return false
        }

        return true
    }
}