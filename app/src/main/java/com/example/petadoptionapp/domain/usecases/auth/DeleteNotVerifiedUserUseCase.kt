package com.example.petadoptionapp.domain.usecases.auth

import com.example.petadoptionapp.domain.repo.AuthRepo
import javax.inject.Inject

class DeleteNotVerifiedUserUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {

    suspend fun invoke() = authRepo.deleteNotVerifiedUser()
}