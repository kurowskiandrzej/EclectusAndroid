package com.kurowskiandrzej.eclectusandroid.presentation.view_model

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.LoginResponseDto
import com.kurowskiandrzej.eclectusandroid.domain.use_case.use_case_wrapper.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCases
) : ViewModel() {

    // Email input

    private val _email = MutableLiveData("")
    val email = _email

    private val _emailValidationResult = MutableLiveData<Pair<Boolean, Int?>>(null)
    val emailValidationResult = _emailValidationResult

    fun updateEmailInput(input: String) {
        _email.value = input
        _emailValidationResult.value = useCase.validateEmail(input)
    }

    // Password input

    private val _password = MutableLiveData("")
    val password = _password

    fun updatePasswordInput(input: String) {
        _password.value = input
    }

    // Login Response

    private val _loginResponse = MutableLiveData<Resource<Pair<LoginResponseDto, String>>>(null)
    val loginResponse = _loginResponse

    fun sendLoginRequest() = viewModelScope.launch {
        _loginResponse.value = Resource.loading(null)
        _loginResponse.value = useCase.sendLoginRequest(
            email.value!!,
            password.value!!
        )
    }

    // Button send login request enable state

    private val _buttonSendLoginRequestEnableState = MediatorLiveData<Boolean>().also { state ->
        state.apply {
            addSource(_emailValidationResult) {
                state.value = useCase.buttonLoginRequestEnableState(
                    loginResponse.value,
                    emailValidationResult.value?.first
                )
            }

            addSource(_loginResponse) {
                state.value = useCase.buttonLoginRequestEnableState(
                    loginResponse.value,
                    emailValidationResult.value?.first
                )
            }
        }
    }
    val buttonSendLoginRequestEnableState = _buttonSendLoginRequestEnableState

    // Log in user

    fun logInUser(
        userId: Long,
        token: String,
        navigateToHomeFragment: () -> Unit
    ) = viewModelScope.launch {
        useCase.logIn(userId, token)
        navigateToHomeFragment()
    }
}