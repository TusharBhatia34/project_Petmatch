package com.example.petadoptionapp.domain.usecases.post

import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.repo.PostRepo
import javax.inject.Inject

class EditPostUseCase @Inject constructor(
    private val postRepo: PostRepo
) {

    suspend fun invoke(post: Post,newImages:Boolean) = postRepo.editPost(post,newImages)
}