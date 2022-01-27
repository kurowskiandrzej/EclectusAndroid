package com.kurowskiandrzej.eclectusandroid.data.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val profileName: String?,
    val pin: String?,
    val token: String?,
    val lastLoginDate: String
)