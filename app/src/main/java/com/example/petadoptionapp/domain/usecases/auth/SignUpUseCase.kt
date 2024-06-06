package com.example.petadoptionapp.domain.usecases.auth

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {

    suspend fun invoke(email:String,password:String): StateFlow<Response<Boolean>> {
        return authRepo.createUser(email,password)
    }
}