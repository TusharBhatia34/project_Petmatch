package com.example.petadoptionapp.domain.usecases.post

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.repo.PostRepo
import javax.inject.Inject

class PostCreationUseCase @Inject constructor(
   private val postRepo: PostRepo
) {
  suspend fun invoke(post: Post): Response<Boolean> = postRepo.createPost(post)
}