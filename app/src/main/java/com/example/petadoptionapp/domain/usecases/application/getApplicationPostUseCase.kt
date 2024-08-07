package com.example.petadoptionapp.domain.usecases.application

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.repo.ApplicationRepo
import com.google.firebase.Timestamp
import javax.inject.Inject

class GetApplicationPostUseCase @Inject constructor(
    private val applicationRepo: ApplicationRepo
) {
    suspend  fun invoke(authorId:String,timestamp: Timestamp):Pair<Post?, Response<Boolean>> = applicationRepo.getApplicationPost(authorId, timestamp)

}