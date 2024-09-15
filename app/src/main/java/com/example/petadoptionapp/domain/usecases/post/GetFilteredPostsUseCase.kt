package com.example.petadoptionapp.domain.usecases.post

import com.example.petadoptionapp.domain.repo.PostRepo
import javax.inject.Inject

class GetFilteredPostsUseCase @Inject constructor(
    private val postRepo: PostRepo
) {

    suspend fun invoke(type: String, gender: String, breed: String, city: String, state: String, country: String) = postRepo.getFilteredPosts(type, gender, breed, city, state, country)
}