package com.kurowskiandrzej.eclectusandroid.data.roomdb

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.kurowskiandrzej.eclectusandroid.data.roomdb.entity.User

@Dao
interface EclectusDao {

    @Query("""
        INSERT INTO User (id, profileName, pin, token, lastLoginDate)
        VALUES (:userId, :profileName, :pin, :token, CURRENT_TIMESTAMP)
    """)
    suspend fun insertUser(
        userId: Long,
        profileName: String?,
        pin: String?,
        token: String
    )

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM User
            WHERE id = :userId
        )
    """)
    suspend fun userWithIdExists(userId: Long): Boolean

    @Query("""
        UPDATE User 
        SET profileName = :profileName
        WHERE id = :userId
    """)
    suspend fun updateUserProfileName(
        userId: Long,
        profileName: String?
    )

    @Query(
        """
        UPDATE User 
        SET pin = :pin
        WHERE id = :userId
    """
    )
    suspend fun updateUserPin(
        userId: Long,
        pin: String?
    )

    @Query(
        """
        UPDATE User
        SET token = :token
        WHERE id = :userId
    """
    )
    suspend fun updateUserToken(userId: Long, token: String?)

    @Query(
        """
        UPDATE User 
        SET lastLoginDate = CURRENT_TIMESTAMP
        WHERE id = :userId
    """
    )
    suspend fun updateUserLastLoginDate(
        userId: Long
    )

    @Query("""
        SELECT * FROM User
        ORDER BY lastLoginDate DESC
        LIMIT 1
    """)
    suspend fun getLastLoggedUser(): User?

    @Transaction
    suspend fun logInUser(userId: Long, token: String) {
        if (userWithIdExists(userId)) {
            updateUserToken(userId, token)
            updateUserLastLoginDate(userId)
        } else {
            insertUser(
                userId,
                null,
                null,
                token
            )
        }
    }

    @Transaction
    suspend fun logOutUser() {
        val userId = getLastLoggedUser()?.id
        userId?.let { id ->
            updateUserToken(id, null)
        }
    }
}