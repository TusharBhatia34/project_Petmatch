package com.example.petadoptionapp.domain.usecases.post

import com.example.petadoptionapp.domain.repo.PostRepo
import com.google.firebase.Timestamp
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepo: PostRepo
){
    suspend fun invoke(authorId:String,timestamp: Timestamp) = postRepo.deletePost(authorId,timestamp)
}