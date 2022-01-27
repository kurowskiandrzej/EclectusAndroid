package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.domain.repository.EclectusRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: EclectusRepository
) {
    suspend operator fun invoke(
        userId: Long,
        profileName: String?,
        pin: String?,
        token: String
    ) {
        repository.insertUser(
            userId,
            profileName,
            pin,
            token
        )
    }
}