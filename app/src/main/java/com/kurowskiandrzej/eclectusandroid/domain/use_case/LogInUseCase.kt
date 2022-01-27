package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.domain.repository.EclectusRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: EclectusRepository
)  {
    suspend operator fun invoke(userId: Long, token: String) {
        repository.logInUser(userId, token)
    }
}