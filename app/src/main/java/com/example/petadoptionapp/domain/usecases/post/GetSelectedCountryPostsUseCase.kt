package com.example.petadoptionapp.domain.usecases.post

import com.example.petadoptionapp.domain.repo.PostRepo
import javax.inject.Inject

class GetSelectedCountryPostsUseCase @Inject constructor(
    private val postRepo: PostRepo
) {
    suspend fun invoke(country:String) = postRepo.getSelectedCountryPosts(country)
}