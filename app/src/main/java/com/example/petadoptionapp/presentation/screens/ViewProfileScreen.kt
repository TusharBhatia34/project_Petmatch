package com.example.petadoptionapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.domain.model.Location
import com.example.petadoptionapp.presentation.viewModels.ProfileViewModel

@Composable
fun ViewProfileScreen(
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
            location = "${profile.city},${profile.state},${profile.country}",
            screenTitle = "Profile" ,
            currentLocation = Location(profile.latitude,profile.longitude) ,
            buttonText = "Edit",
            buttonAction = {name,location,about,profilePicture ,locationInDouble,sameImage->}, //we won't use this button action in viewProfileScreen.
            responseAction = { /*TODO*/ },
            navController = navController ,
            response = response,
            enabled = false
        )
    }

}