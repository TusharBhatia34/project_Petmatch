package com.example.petadoptionapp.data.local.gettingCurrentLocation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.example.petadoptionapp.data.common.Response
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.tasks.await

class DefaultLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient
){

  private  fun emptyLocation(): Location {
        val location = Location(LocationManager.PASSIVE_PROVIDER)
        location.latitude = 0.0
        location.longitude = 0.0
        return location
    }
    @SuppressLint("MissingPermission")
     suspend fun getLocationUpdates(): Pair<Location, Response<Boolean>>{
try {
    if(!context.hasLocationPermission()) {
        return Pair(emptyLocation(), Response.Failure(Exception("locationPermission")))
    }

    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    if(!isGpsEnabled && !isNetworkEnabled) {
        return Pair(emptyLocation(), Response.Failure(Exception("gps")))
    }

    val request = LocationRequest.create()



    val locationDeferred = CompletableDeferred<Location>()
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {

            super.onLocationResult(result)
            result.locations.lastOrNull()?.let {location ->
                locationDeferred.complete(location)
            }
        }
    }

    client.requestLocationUpdates(
        request,
        locationCallback,
        Looper.getMainLooper()
    ).await()

    val currentLocation: Location = locationDeferred.await()
    return Pair(currentLocation, Response.Success(true))

}
catch (e:Exception){
    return Pair(emptyLocation(), Response.Failure(Exception("There is some issue.")))
}


    }

}
