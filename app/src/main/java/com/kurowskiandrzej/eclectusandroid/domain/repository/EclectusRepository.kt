package com.kurowskiandrzej.eclectusandroid.domain.repository

import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.LoginResponseDto
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.RegisterResponseDto
import com.kurowskiandrzej.eclectusandroid.data.roomdb.entity.User

interface EclectusRepository {
    suspend fun getLastLoggedUser(): User?

    suspend fun insertUser(
        id: Long,
        profileName: String?,
        pin: String?,
        token: String
    )

    suspend fun updateUserToken(userId: Long, token: String?)

    suspend fun logInUser(userId: Long, token: String)

    suspend fun logOutUser()

    suspend fun sendLoginRequest(
        email: String,
        password: String
    ): Resource<Pair<LoginResponseDto, String>>

    suspend fun sendRegisterRequest(
        email: String,
        password: String,
        passwordConfirmation: String
    ): Resource<Pair<RegisterResponseDto, String>>
}