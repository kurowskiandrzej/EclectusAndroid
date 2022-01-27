package com.kurowskiandrzej.eclectusandroid.presentation.view_model

import androidx.lifecycle.ViewModel
import com.kurowskiandrzej.eclectusandroid.data.roomdb.entity.User
import com.kurowskiandrzej.eclectusandroid.domain.use_case.use_case_wrapper.LoadUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoadUserViewModel @Inject constructor(
    private val useCase: LoadUserUseCases
) : ViewModel() {
    suspend fun getLastLoggedUser(): User? {
        return useCase.getLastLoggedUser()
    }
}