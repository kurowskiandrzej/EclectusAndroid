package com.kurowskiandrzej.eclectusandroid.domain.use_case

import com.kurowskiandrzej.eclectusandroid.data.roomdb.entity.User
import com.kurowskiandrzej.eclectusandroid.domain.repository.EclectusRepository
import javax.inject.Inject

class LoadLastUserUseCase @Inject constructor(
    private val repository: EclectusRepository
) {
    suspend operator fun invoke(): User? {
        return repository.getLastLoggedUser()
    }
}