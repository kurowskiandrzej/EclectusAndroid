package com.kurowskiandrzej.eclectusandroid.domain.use_case.use_case_wrapper

import com.kurowskiandrzej.eclectusandroid.domain.use_case.LogInUseCase
import com.kurowskiandrzej.eclectusandroid.domain.use_case.LogOutUseCase
import javax.inject.Inject

data class HomeUseCases @Inject constructor(
    val logOut: LogOutUseCase
)