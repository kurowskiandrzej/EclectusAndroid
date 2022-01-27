package com.kurowskiandrzej.eclectusandroid.presentation.view_model

import com.kurowskiandrzej.eclectusandroid.domain.use_case.LogOutUseCase
import com.kurowskiandrzej.eclectusandroid.domain.use_case.use_case_wrapper.HomeUseCases
import com.kurowskiandrzej.eclectusandroid.presentation.data.repository.EclectusRepositoryFake
import org.junit.Before

class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(getUseCases())
    }

    private fun getUseCases(): HomeUseCases {
        return HomeUseCases(
            LogOutUseCase(
                EclectusRepositoryFake()
            )
        )
    }
}