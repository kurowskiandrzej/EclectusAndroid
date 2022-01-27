package com.kurowskiandrzej.eclectusandroid.domain.use_case.use_case_wrapper

import com.kurowskiandrzej.eclectusandroid.domain.use_case.*
import javax.inject.Inject

class RegisterUseCases @Inject constructor(
    val sendRegisterRequest: SendRegisterRequestUseCase,
    val validateEmailInput: ValidateEmailInputUseCase,
    val validatePasswordInput: ValidatePasswordInputUseCase,
    val validatePasswordConfirmationInput: ValidatePasswordConfirmationInputUseCase,
    val buttonRegisterRequestEnableState: ButtonRegisterRequestEnableStateUseCase,
    val insertUser: InsertUserUseCase,
    val logIn: LogInUseCase,
)