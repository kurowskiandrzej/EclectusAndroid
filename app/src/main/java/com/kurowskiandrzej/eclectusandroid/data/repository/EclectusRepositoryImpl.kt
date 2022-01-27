package com.kurowskiandrzej.eclectusandroid.data.repository

import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.data.remote.EclectusApi
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.LoginResponseDto
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.RegisterResponseDto
import com.kurowskiandrzej.eclectusandroid.data.roomdb.EclectusDao
import com.kurowskiandrzej.eclectusandroid.data.roomdb.entity.User
import com.kurowskiandrzej.eclectusandroid.domain.repository.EclectusRepository
import javax.inject.Inject

class EclectusRepositoryImpl @Inject constructor(
    private val dao: EclectusDao,
    private val api: EclectusApi
) : EclectusRepository {
    override suspend fun getLastLoggedUser(): User? {
        return dao.getLastLoggedUser()
    }

    override suspend fun insertUser(
        id: Long,
        profileName: String?,
        pin: String?,
        token: String
    ) {
        dao.insertUser(
            id,
            profileName,
            pin,
            token
        )
    }

    override suspend fun updateUserToken(userId: Long, token: String?) {
        dao.updateUserToken(userId, token)
    }

    override suspend fun logInUser(userId: Long, token: String) {
        dao.logInUser(userId, token)
    }

    override suspend fun logOutUser() {
        dao.logOutUser()
    }

    override suspend fun sendLoginRequest(
        email: String,
        password: String
    ): Resource<Pair<LoginResponseDto, String>> {
        api.sendLoginRequest(
            email,
            password
        ).apply {
            val token: String = headers().let { headers ->
                headers["set-cookie"].toString()
            }

            body()?.let { body ->
                return Resource.success(Pair(body, token))
            }
        }
        // TODO better error message
        return Resource.error("connection error", null)
    }

    override suspend fun sendRegisterRequest(
        email: String,
        password: String,
        passwordConfirmation: String
    ): Resource<Pair<RegisterResponseDto, String>> {
        api.sendRegisterRequest(
            email,
            password,
            passwordConfirmation
        ).apply {
            val token: String = headers().let { headers ->
               headers["set-cookie"].toString()
            }

            body()?.let { body ->
                return Resource.success(Pair(body, token))
            }
        }
        return Resource.error("connection error", null)
    }
}