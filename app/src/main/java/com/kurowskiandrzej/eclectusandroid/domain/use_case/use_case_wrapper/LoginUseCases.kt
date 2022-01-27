package com.kurowskiandrzej.eclectusandroid.domain.use_case.use_case_wrapper

import com.kurowskiandrzej.eclectusandroid.domain.use_case.*
import javax.inject.Inject

class LoginUseCases @Inject constructor(
    val sendLoginRequest: SendLoginRequestUseCase,
    val validateEmail: ValidateEmailInputUseCase,
    val buttonLoginRequestEnableState: ButtonLoginRequestEnableStateUseCase,
    val logIn: LogInUseCase
)