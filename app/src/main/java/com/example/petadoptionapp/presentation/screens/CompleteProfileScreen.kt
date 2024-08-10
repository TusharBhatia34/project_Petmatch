package com.example.petadoptionapp.presentation.screens

import android.Manifest
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.presentation.viewModels.ProfileViewModel

@Composable
fun CompleteProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    activity: Activity,
    navController: NavController
) {
    if(!profileViewModel.hasLocationPermission()){

        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            0
        )
    }
    else {
        val currLocation = profileViewModel.location.collectAsStateWithLifecycle()
        val response = profileViewModel.profileSavedResponse.collectAsStateWithLifecycle()
       ProfileScreen(
           name = "",
           about = "",
           profilePicture = "",
           location = "",
           screenTitle = "Complete your profile",
           currentLocation = currLocation.value,
           buttonText = "Save",
           buttonAction = { name,location,about,profilePicture ,locationInDouble,sameImage->
                          profileViewModel.saveProfile(name, location, about, profilePicture,locationInDouble,sameImage)
           },
           responseAction = {
               navController.navigate(Routes.HomeScreen)
               profileViewModel.resetValue()
                            },
           navController = navController,
           response = response
       )
    }
}