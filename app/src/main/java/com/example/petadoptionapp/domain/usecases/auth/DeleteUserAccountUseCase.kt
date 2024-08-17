package com.example.petadoptionapp.domain.usecases.auth

import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.repo.AuthRepo
import javax.inject.Inject

class DeleteUserAccountUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {
    suspend fun invoke() = authRepo.deleteUserAccount(authorId = SharedComponents.currentUser!!.uid)
}