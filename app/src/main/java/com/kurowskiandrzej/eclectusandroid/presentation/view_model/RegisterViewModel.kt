package com.kurowskiandrzej.eclectusandroid.presentation.view_model

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.RegisterResponseDto
import com.kurowskiandrzej.eclectusandroid.domain.use_case.use_case_wrapper.RegisterUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCases
): ViewModel() {

    // Email input

    private val _emailInput = MutableLiveData("")
    private val emailInput = _emailInput

    private val _emailInputValidationResult = MutableLiveData<Pair<Boolean, Int?>>(null)
    val emailInputValidationResult = _emailInputValidationResult

    fun updateEmailInput(emailInput: String) {
        _emailInput.value = emailInput
        _emailInputValidationResult.value = useCase.validateEmailInput(emailInput)
    }

    // Password input

    private val _passwordInput = MutableLiveData("")
    private val passwordInput = _passwordInput

    private val _passwordInputValidationResult = MutableLiveData<Pair<Boolean, Int?>>(null)
    val passwordInputValidationResult = _passwordInputValidationResult

    fun updatePasswordInput(
        passwordInput: String,
        passwordConfirmationInput: String
    ) {
        _passwordInput.value = passwordInput
        _passwordInputValidationResult.value = useCase.validatePasswordInput(passwordInput)
        validatePasswordConfirmation(
            passwordConfirmationInput,
            passwordInput
        )
    }

    // Password confirmation input

    private val _passwordConfirmationInput = MutableLiveData("")
    private val passwordConfirmationInput = _passwordConfirmationInput

    private val _passwordConfirmationInputValidationResult = MutableLiveData<Pair<Boolean, Int?>>(null)
    val passwordConfirmationInputValidationResult = _passwordConfirmationInputValidationResult

    fun updatePasswordConfirmationInput(
        passwordConfirmationInput: String,
        passwordInput: String
    ) {
        _passwordConfirmationInput.value = passwordConfirmationInput
        validatePasswordConfirmation(
            passwordConfirmationInput,
            passwordInput
        )
    }

    private fun validatePasswordConfirmation(
        passwordConfirmationInput: String,
        passwordInput: String
    ) {
        _passwordConfirmationInputValidationResult.value = useCase.validatePasswordConfirmationInput(
            passwordConfirmationInput,
            passwordInput
        )
    }

    // Register response

    private val _registerResponse = MutableLiveData<Resource<Pair<RegisterResponseDto, String>>>(null)
    val registerResponse = _registerResponse

    fun sendRegisterRequest() = viewModelScope.launch {
        _registerResponse.value = Resource.loading(null)
        _registerResponse.value = useCase.sendRegisterRequest(
            emailInput.value!!,
            passwordInput.value!!,
            passwordConfirmationInput.value!!
        )
    }

    // Button send register request enable state

    private val _buttonSendRegisterRequestEnableState = MediatorLiveData<Boolean>().also { state ->
        state.apply {
            addSource(_registerResponse) {
                state.value = getButtonSendRegisterRequestEnableState()
            }

            addSource(_emailInputValidationResult) {
                state.value = getButtonSendRegisterRequestEnableState()
            }

            addSource(_passwordInputValidationResult) {
                state.value = getButtonSendRegisterRequestEnableState()
            }

            addSource(_passwordConfirmationInputValidationResult) {
                state.value = getButtonSendRegisterRequestEnableState()
            }
        }
    }
    val buttonSendRegisterRequestEnableState = _buttonSendRegisterRequestEnableState

    private fun getButtonSendRegisterRequestEnableState(): Boolean {
        return useCase.buttonRegisterRequestEnableState(
            registerResponse.value,
            emailInputValidationResult.value?.first,
            passwordInputValidationResult.value?.first,
            passwordConfirmationInputValidationResult.value?.first
        )
    }

    // Register user

    fun registerUser(
        userId: Long,
        profileName: String?,
        pin: String?,
        token: String,
        navigateToHomeFragment: () -> Unit
    ) = viewModelScope.launch {
        useCase.insertUser(
            userId,
            profileName,
            pin,
            token
        )
        navigateToHomeFragment()
    }
}