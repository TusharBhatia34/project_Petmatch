package com.example.petadoptionapp.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.local.cachingUserProfile.UserDatastore
import com.example.petadoptionapp.data.local.gettingCurrentLocation.DefaultLocationClient
import com.example.petadoptionapp.data.local.gettingCurrentLocation.hasLocationPermission
import com.example.petadoptionapp.domain.model.Location
import com.example.petadoptionapp.domain.model.UserProfile
import com.example.petadoptionapp.domain.usecases.profile.ProfileExistsUseCase
import com.example.petadoptionapp.domain.usecases.profile.SaveProfileUseCase
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val saveProfileUseCase: SaveProfileUseCase,
    private val applicationContext: Context,
    private val profileExistsUseCase: ProfileExistsUseCase,
    private val userDatastore: UserDatastore
): ViewModel() {
    private var _profileSavedResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    var profileSavedResponse = _profileSavedResponse.asStateFlow()

    private var _profileExistsResponse =MutableStateFlow<Response<Boolean>>(Response.Loading)
    val profileExistsResponse = _profileExistsResponse.asStateFlow()

    private var _location = MutableStateFlow(Location())
    var location = _location.asStateFlow()
    private var _userProfile = MutableStateFlow<UserProfile?>(null)
    var userProfile = _userProfile.asStateFlow()

    private var _profileExists = MutableStateFlow(true)
    val profileExists = _profileExists.asStateFlow()


    private var _gettingCurrentLocationResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    var gettingCurrentLocationResponse = _gettingCurrentLocationResponse.asStateFlow()


    init {
        if(applicationContext.hasLocationPermission()){
        getCurrentLocation()
    }
        doesProfileExists()
        getUserProfile()
    }

    fun hasLocationPermission():Boolean{
        return applicationContext.hasLocationPermission()
    }
   private fun getCurrentLocation(){
        val locationClient = DefaultLocationClient(
           applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )

        viewModelScope.launch(Dispatchers.IO) {
            val result = locationClient
                .getLocationUpdates()
            val location = result.first
            _location.value = Location(lat = location.latitude,long = location.longitude)
            _gettingCurrentLocationResponse.value = result.second

        }
    }

    fun resetValue(){
        _profileSavedResponse.value = Response.Loading
    }

    fun checkProfileExists(){
        viewModelScope.launch {
           _profileExistsResponse.value = profileExistsUseCase.invoke()
        }
    }

   private fun doesProfileExists(){
        viewModelScope.launch {
       userDatastore.profileExists.collect{
           _profileExists.value = it?:false
       }
        }
    }

 private  fun getUserProfile(){
        viewModelScope.launch {
            userDatastore.getProfileInfo().collect{
                _userProfile.value = it
            }

        }
    }

    fun saveProfile(
        name: String,
        country:String,
        about: String,
        profilePicture: String,
        sameImage:Boolean

    ){

        val userProfile = UserProfile(
            name = name,
            country = country,
            about = about,
            profilePicture = profilePicture
        )
        viewModelScope.launch {
            _profileSavedResponse.value = saveProfileUseCase.invoke(userProfile,sameImage)
             
        }
    }
}