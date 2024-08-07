package com.example.petadoptionapp.presentation.screens

import android.location.Geocoder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petadoptionapp.ui.theme.AppTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.delay
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(lat:Double, long:Double, navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
        var current by remember {
            mutableStateOf(LatLng(lat,long))
        }

        val cameraPositionState = rememberCameraPositionState{
            position = CameraPosition.fromLatLngZoom(current,10f)
        }
        val markerState = rememberMarkerState(position =current)
    Box(modifier = Modifier.fillMaxSize()){
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState){
            Marker(
                state = markerState,
                title = "Current Location",
                draggable = true,
            )
        }
        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null,modifier = Modifier
            .clickable { navController.popBackStack() }
            .padding(16.dp), tint = Color.Black )

        val geocoder = Geocoder(LocalContext.current, Locale.getDefault())
        val addresses =  geocoder.getFromLocation(current.latitude, current.longitude, 1)

        BottomSheetScaffold(sheetContent = {

            Column(modifier = Modifier
                .height(screenHeight.dp / 4f)
                .padding(horizontal = AppTheme.dimens.mediumLarge),
                ) {
                Text(
                    text = "Location",
                    modifier = Modifier
                        .padding(vertical = AppTheme.dimens.mediumLarge)
                      ,
                    color = MaterialTheme.colorScheme.onBackground,

                    style = MaterialTheme.typography.headlineLarge
                )

                if (!addresses.isNullOrEmpty()) {
                    val location = "${addresses[0].locality},${addresses[0].adminArea}, ${addresses[0].countryName}"

                    Text(text = location,fontWeight = FontWeight.Normal, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        navController.previousBackStackEntry?.savedStateHandle?.set("locationInDouble" , Pair(markerState.position.latitude,markerState.position.longitude))
                        navController.previousBackStackEntry?.savedStateHandle?.set("location",location)
                        navController.popBackStack()

                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary

                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = AppTheme.dimens.large,
                                vertical = AppTheme.dimens.mediumLarge
                            )
                           ,
                        shape = RoundedCornerShape(8.dp)
                    ) {

                        Text(text = "Add this location", color = MaterialTheme.colorScheme.onPrimary)


                    }



                }

            }
        }, sheetSwipeEnabled = false,
            sheetPeekHeight = screenHeight.dp/4f,
            sheetDragHandle = { }) {

        }

        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val cityName = addresses[0].locality
                val stateName = addresses[0].adminArea
                val countryName = addresses[0].countryName
            }
        }
        LaunchedEffect(key1 = markerState.position) {
            delay(1000L)
            current = LatLng(markerState.position.latitude, markerState.position.longitude)
        }
    }

}


