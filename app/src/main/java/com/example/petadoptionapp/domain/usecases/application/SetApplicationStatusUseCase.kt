package com.example.petadoptionapp.domain.usecases.application

import com.example.petadoptionapp.domain.repo.ApplicationRepo
import javax.inject.Inject

class SetApplicationStatusUseCase @Inject constructor(
    private val applicationRepo: ApplicationRepo
) {
    suspend fun invoke(
        documentId:String,
        status: String
    ) = applicationRepo.setApplicationStatus(documentId , status)
}