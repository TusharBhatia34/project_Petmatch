package com.example.petadoptionapp.domain.usecases.application

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.repo.ApplicationRepo
import com.google.firebase.Timestamp
import javax.inject.Inject

class SendingApplicationUseCase @Inject constructor(
    private val applicationRepo: ApplicationRepo
) {

    suspend  fun invoke(
        answers: List<String>,
        authorId: String,
        applicantId: String,
        postTimeStamp: Timestamp,
        applicantProfilePicture: String,
        applicantUserName: String,
        petName: String,
    ): Response<Boolean>
    = applicationRepo.sendApplication(
        answers,
        authorId,
        applicantId,
        postTimeStamp,
        applicantProfilePicture,
        applicantUserName,
        petName
    )

}