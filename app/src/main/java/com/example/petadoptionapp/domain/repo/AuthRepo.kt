package com.example.petadoptionapp.domain.repo

import com.example.petadoptionapp.data.common.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepo {
    suspend fun createUser(email: String,password:String): StateFlow<Response<Boolean>>
    suspend fun signIn(email: String,password:String): Flow<Response<Boolean>>
    suspend fun reloadUser():Response<Boolean>
    suspend fun signOut()

    suspend fun resetPassword(email: String):Flow<Response<Boolean>>

    suspend fun deleteNotVerifiedUser():Response<Boolean>

    suspend fun resendVerificationEmail():Response<Boolean>

}