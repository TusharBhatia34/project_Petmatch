package com.example.petadoptionapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.domain.model.Location
import com.example.petadoptionapp.presentation.viewModels.ProfileViewModel

@Composable
fun EditProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    val userProfile =profileViewModel.userProfile.collectAsStateWithLifecycle()
    val response = profileViewModel.profileSavedResponse.collectAsStateWithLifecycle()
    userProfile.value?.let {profile ->
        ProfileScreen(
            name = profile.name,
            about = profile.about ,
            profilePicture = profile.profilePicture ,
            location = "${profile.city},${profile.state}, ${profile.country}",
            screenTitle = "Edit Profile" ,
            currentLocation = Location(profile.latitude,profile.longitude) ,
            buttonText = "Save",
            buttonAction = { name,location,about,profilePicture ,locationInDouble->
                profileViewModel.saveProfile(name, location, about, profilePicture,locationInDouble)
            },
            responseAction = {
                             navController.navigate(Routes.ViewProfileScreen)
                profileViewModel.resetValue()
            },
            navController = navController ,
            response = response,
            enabled = true
        )
    }
}