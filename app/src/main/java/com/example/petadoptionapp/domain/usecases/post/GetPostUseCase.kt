package com.example.petadoptionapp.domain.usecases.post

import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.repo.PostRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepo: PostRepo
) {
    suspend fun invoke(): Flow<List<Post>> = postRepo.getPost()
}