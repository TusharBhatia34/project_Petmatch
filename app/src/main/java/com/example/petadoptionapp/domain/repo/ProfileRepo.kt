package com.example.petadoptionapp.domain.repo

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.UserProfile

interface ProfileRepo {
    suspend fun editProfile()
    suspend fun saveProfile(userProfile: UserProfile,sameImage:Boolean):Response<Boolean>

    suspend fun userProfile():Response<Boolean>


}