package com.example.petadoptionapp.domain.usecases.profile

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.repo.ProfileRepo
import javax.inject.Inject

class ProfileExistsUseCase @Inject constructor(
    private val profileRepo: ProfileRepo
) {

    suspend fun invoke(): Response<Boolean> = profileRepo.userProfile()
}