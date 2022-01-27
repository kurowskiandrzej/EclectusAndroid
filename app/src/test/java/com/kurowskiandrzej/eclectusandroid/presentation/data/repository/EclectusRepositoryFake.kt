package com.kurowskiandrzej.eclectusandroid.presentation.data.repository

import androidx.lifecycle.MutableLiveData
import com.kurowskiandrzej.eclectusandroid.common.Resource
import com.kurowskiandrzej.eclectusandroid.common.TestConstants
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.LoginResponseDto
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.RegisterResponseDto
import com.kurowskiandrzej.eclectusandroid.data.roomdb.entity.User
import com.kurowskiandrzej.eclectusandroid.domain.repository.EclectusRepository

class EclectusRepositoryFake : EclectusRepository {
    private var wasUserLogged = true

    fun wasUserLogged(state: Boolean) {
        wasUserLogged = state
    }

    private val users = mutableListOf<User>()
    private val usersLiveData = MutableLiveData<List<User>>(users)

    override suspend fun getLastLoggedUser(): User? {
        return if (wasUserLogged) {
            User(
                TestConstants.FAKE_USER_ID,
                null,
                null,
                TestConstants.FAKE_USER_TOKEN,
                TestConstants.FAKE_USER_LAST_LOGIN_DATE
            )
        } else null
    }

    override suspend fun insertUser(
        id: Long,
        profileName: String?,
        pin: String?,
        token: String
    ) {
        users.add(
            User(
                id,
                profileName,
                pin,
                token,
                TestConstants.FAKE_USER_LAST_LOGIN_DATE
            )
        )
    }

    override suspend fun updateUserToken(userId: Long, token: String?) {
        val userIndex = users.indexOf(users.find { user ->
            user.id == userId
        })
        users[userIndex] = User(
            users[userIndex].id,
            users[userIndex].profileName,
            users[userIndex].pin,
            TestConstants.FAKE_UPDATED_USER_TOKEN,
            users[userIndex].lastLoginDate
        )
    }

    override suspend fun logInUser(userId: Long, token: String) {
        users.add(
            User(
                TestConstants.FAKE_USER_ID,
                null,
                null,
                TestConstants.FAKE_USER_TOKEN,
                TestConstants.FAKE_USER_LAST_LOGIN_DATE
            )
        )
    }

    override suspend fun logOutUser() {
        // TODO("Not yet implemented")
    }

    override suspend fun sendLoginRequest(email: String, password: String): Resource<Pair<LoginResponseDto, String>> {
        return Resource.success(
            Pair(
                LoginResponseDto(TestConstants.FAKE_USER_ID),
                TestConstants.FAKE_UPDATED_USER_TOKEN
            )
        )
    }

    override suspend fun sendRegisterRequest(
        email: String,
        password: String,
        passwordConfirmation: String
    ): Resource<Pair<RegisterResponseDto, String>> {
        return Resource.success(
            Pair(
                RegisterResponseDto(TestConstants.FAKE_USER_ID),
                TestConstants.FAKE_UPDATED_USER_TOKEN
            )
        )
    }
}