package com.example.petadoptionapp.domain.usecases.post

import com.example.petadoptionapp.domain.repo.PostRepo
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepo: PostRepo
) {
    suspend fun invoke(country:String) = postRepo.getPost(country)
}