package com.example.petadoptionapp.domain.usecases.savedPost

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.SavedPost
import com.example.petadoptionapp.domain.repo.PostRepo
import javax.inject.Inject

class SavePostUseCase @Inject constructor(
    private val postRepo: PostRepo
) {
    suspend operator fun invoke(savedPost: SavedPost):Response<Boolean> = postRepo.savePost(savedPost)

}