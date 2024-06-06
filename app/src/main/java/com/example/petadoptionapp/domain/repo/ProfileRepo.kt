package com.example.petadoptionapp.domain.repo

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    suspend fun editProfile()
    suspend fun saveProfile(userProfile: UserProfile): Flow<Response<Boolean>>
}