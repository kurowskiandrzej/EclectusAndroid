package com.kurowskiandrzej.eclectusandroid.domain.use_case.use_case_wrapper

import com.kurowskiandrzej.eclectusandroid.domain.use_case.LoadLastUserUseCase
import javax.inject.Inject

data class LoadUserUseCases @Inject constructor(
    val getLastLoggedUser: LoadLastUserUseCase
)