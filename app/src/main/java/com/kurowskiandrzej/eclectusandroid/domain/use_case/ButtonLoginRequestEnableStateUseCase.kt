package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.common.Status
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.LoginResponseDto
import javax.inject.Inject

class ButtonLoginRequestEnableStateUseCase @Inject constructor() {
    operator fun invoke(
        loginRequestResponse: Resource<Pair<LoginResponseDto, String>>?,
        emailInputValidation: Boolean?
    ): Boolean {
        emailInputValidation?.let { validationResult ->
            if (!validationResult) return false
        } ?: return false

        loginRequestResponse?.let { response ->
            if (response.status == Status.LOADING) return false
        } ?: return true

        return true
    }
}