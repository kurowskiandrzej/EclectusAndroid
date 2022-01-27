package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.domain.repository.EclectusRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val repository: EclectusRepository
) {
    suspend operator fun invoke() {
        repository.logOutUser()
    }
}