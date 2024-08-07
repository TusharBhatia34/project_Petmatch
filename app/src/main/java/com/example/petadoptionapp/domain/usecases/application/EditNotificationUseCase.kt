package com.example.petadoptionapp.domain.usecases.application

import com.example.petadoptionapp.domain.repo.ApplicationRepo
import com.google.firebase.Timestamp
import javax.inject.Inject

class EditNotificationUseCase
@Inject constructor(
    private val applicationRepo: ApplicationRepo
) {

    suspend  fun invoke(applicantId:String,authorId:String,timestamp: Timestamp) = applicationRepo.editNotification(applicantId,authorId,timestamp)

}