package com.example.petadoptionapp.domain.usecases.savedPost

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.repo.PostRepo
import com.google.firebase.Timestamp
import javax.inject.Inject

class RemoveSavedPostUseCase @Inject constructor(
   private val  postRepo: PostRepo
) {
    suspend  fun invoke(
        authorId: String,
        timeStamp: Timestamp,
        savedBy: String,
    ):Response<Boolean> {
      return postRepo.removeSavedPost(authorId,timeStamp,savedBy)
    }

}