package com.example.petadoptionapp.domain.usecases.auth

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
private val authRepo: AuthRepo
) {
    suspend fun invoke(email:String,password:String): Flow<Response<Boolean>> = authRepo.signIn(email,password)
}