package com.example.petadoptionapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Collections
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.UserProfile
import com.example.petadoptionapp.domain.usecases.profile.SaveProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val saveProfileUseCase: SaveProfileUseCase
): ViewModel() {
    private var _profileSaved = MutableStateFlow<Response<Boolean>>(Response.Loading)
    var profileSaved = _profileSaved.asStateFlow()

    fun saveProfile(
        username: String,
        state: String,
        country: String,
        description: String,
        profilePicture: String,
        city: String,
    ){
        val userProfile = UserProfile(
            username = username,
            country = country,
            state = state,
            description = description,
            profilePicture = profilePicture,
            city = city,
            authorId = Collections.currentUser?.uid!!
        )
        viewModelScope.launch {
            saveProfileUseCase.invoke(userProfile)
                .collect{
                    _profileSaved.value = it
                }
        }
    }
}