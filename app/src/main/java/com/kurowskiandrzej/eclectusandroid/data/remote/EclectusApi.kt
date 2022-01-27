package com.kurowskiandrzej.eclectusandroid.data.remote

import com.kurowskiandrzej.eclectusandroid.data.remote.dto.LoginResponseDto
import com.kurowskiandrzej.eclectusandroid.data.remote.dto.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.*

interface EclectusApi {

    @FormUrlEncoded
    @POST("/api/login")
    suspend fun sendLoginRequest(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponseDto>

    @FormUrlEncoded
    @POST("/api/register")
    suspend fun sendRegisterRequest(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("passwordConfirmation") passwordConfirmation: String
    ): Response<RegisterResponseDto>

    @GET("/api/flashcards")
    suspend fun getFlashcards(
        @Header("Cookie") token: String
    )
}