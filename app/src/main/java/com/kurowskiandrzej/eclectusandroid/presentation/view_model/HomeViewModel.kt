package com.kurowskiandrzej.eclectusandroid.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurowskiandrzej.eclectusandroid.domain.use_case.use_case_wrapper.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCases
) : ViewModel() {
    fun logOut(
        navigateToLoginFragment: () -> Unit
    ) = viewModelScope.launch {
        useCase.logOut()
        navigateToLoginFragment()
    }
}