package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.LoginResponseDto
import com.kurowskiandrzej.eclectusandroid.domain.repository.EclectusRepository
import javax.inject.Inject

class SendLoginRequestUseCase @Inject constructor(
    private val repository: EclectusRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Resource<Pair<LoginResponseDto, String>> {

        return repository.sendLoginRequest(
            email,
            password
        )
    }
}