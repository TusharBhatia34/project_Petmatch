package com.example.petadoptionapp.domain.usecases.auth

import com.example.petadoptionapp.domain.repo.AuthRepo
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {
    suspend fun invoke(email:String)= authRepo.resetPassword(email)
}