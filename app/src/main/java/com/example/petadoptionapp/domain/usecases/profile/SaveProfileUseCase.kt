package com.example.petadoptionapp.domain.usecases.profile

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.UserProfile
import com.example.petadoptionapp.domain.repo.ProfileRepo
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val profileRepo: ProfileRepo
) {

    suspend fun invoke(userProfile: UserProfile,sameImage:Boolean): Response<Boolean> = profileRepo.saveProfile(userProfile,sameImage)
}
