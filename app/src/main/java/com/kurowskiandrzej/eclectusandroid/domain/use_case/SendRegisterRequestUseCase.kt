package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.RegisterResponseDto
import com.kurowskiandrzej.eclectusandroid.domain.repository.EclectusRepository
import javax.inject.Inject

class SendRegisterRequestUseCase @Inject constructor(
    private val repository: EclectusRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        passwordConfirmation: String
    ): Resource<Pair<RegisterResponseDto, String>> {
        return repository.sendRegisterRequest(
            email,
            password,
            passwordConfirmation
        )
    }
}