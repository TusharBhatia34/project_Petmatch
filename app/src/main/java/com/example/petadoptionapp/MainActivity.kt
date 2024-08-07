package com.example.petadoptionapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.petadoptionapp.ui.theme.PetAdoptionAppTheme
import com.example.petadoptionapp.ui.theme.rememberWindowSizeClass
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val permissionsState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
            
           val window = rememberWindowSizeClass()
            PetAdoptionAppTheme(windowSizeClass = window) {
              LocationPermissions(multiplePermissionState = permissionsState, activity = this)

                         }
            LaunchedEffect(Unit) {

                permissionsState.launchMultiplePermissionRequest()
            }
                }
            }
        }


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissions(
    multiplePermissionState: MultiplePermissionsState,
    activity: Activity
) {

    PermissionsRequired(
        multiplePermissionsState = multiplePermissionState,
        permissionsNotGrantedContent = {


                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)){

                    AlertDialog(
                        onDismissRequest = {  },
                        title = { Text("Permission Required") },
                        text = { Text("Location permission is needed to use this feature. Please grant the permission.") },
                        confirmButton = {
                            Button(onClick = {
                                multiplePermissionState.launchMultiplePermissionRequest()
                            }) {
                                Text("Grant Permission")
                            }
                        },

                        )
                }


        },
        permissionsNotAvailableContent = {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White))
            AlertDialog(
                onDismissRequest = {  },
                title = { Text("Permission Required") },
                text = { Text("Location permission is needed to use this feature. Please grant the permission.") },
                confirmButton = {
                    Button(onClick = {
                       Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                           Uri.fromParts("package",activity.packageName,null)).also {
                               activity.startActivity(it)
                       }
                    }) {
                        Text("go to settings")
                    }
                },

                )
        }) {
        BottomNavigationBar(activity = activity)
    }
}

