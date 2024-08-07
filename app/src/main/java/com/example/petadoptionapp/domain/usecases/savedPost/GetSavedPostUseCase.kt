package com.example.petadoptionapp.domain.usecases.savedPost

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.repo.PostRepo
import javax.inject.Inject

class GetSavedPostUseCase @Inject constructor(
    private val postRepo: PostRepo
) {
    suspend fun invoke(): Response<Boolean> {
       return  postRepo.getSavedPost()
    }
}