package com.example.petadoptionapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Routes
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
            location = profile.country,
            screenTitle = "Edit Profile" ,
            buttonText = "Save",
            buttonAction = { name,location,about,profilePicture ,sameImage->
                profileViewModel.saveProfile(name, location, about, profilePicture,sameImage)
            },
            responseAction = {
                navController.navigate(Routes.ViewProfileScreen){
                    popUpTo(Routes.ViewProfileScreen){inclusive = true}
                }
                profileViewModel.resetValue()
            },
            navController = navController ,
            response = response,
            enabled = true
        )
    }
}