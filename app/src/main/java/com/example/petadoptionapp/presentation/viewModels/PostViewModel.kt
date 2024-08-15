package com.example.petadoptionapp.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.data.local.cachingAppliedApplications.Entity
import com.example.petadoptionapp.data.local.cachingUserProfile.UserDatastore
import com.example.petadoptionapp.data.local.gettingCurrentLocation.DefaultLocationClient
import com.example.petadoptionapp.data.local.gettingCurrentLocation.hasLocationPermission
import com.example.petadoptionapp.domain.model.Location
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.model.SavedPost
import com.example.petadoptionapp.domain.usecases.application.AddAppliedApplicationLocallyUseCase
import com.example.petadoptionapp.domain.usecases.application.DatabaseIsEmptyUseCase
import com.example.petadoptionapp.domain.usecases.application.GetAppliedApplicationsUseCase
import com.example.petadoptionapp.domain.usecases.post.GetPostUseCase
import com.example.petadoptionapp.domain.usecases.post.PostCreationUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.GetSavedPostUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.RemoveSavedPostUseCase
import com.example.petadoptionapp.domain.usecases.savedPost.SavePostUseCase
import com.google.android.gms.location.LocationServices
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val postCreationUseCase: PostCreationUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val savePostUseCase: SavePostUseCase,
    private val getSavedPostUseCase: GetSavedPostUseCase,
    private val removeSavedPostUseCase: RemoveSavedPostUseCase,
    private val applicationContext: Context,
    private val addAppliedApplicationLocallyUseCase: AddAppliedApplicationLocallyUseCase,
   private val databaseIsEmptyUseCase: DatabaseIsEmptyUseCase,
    private val getAppliedApplicationsUseCase: GetAppliedApplicationsUseCase,
    private val userDatastore: UserDatastore
):ViewModel() {

    private val _post = MutableStateFlow<List<Post>>(emptyList())
    val post = _post.asStateFlow()

    private val _createPostResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val createPostResponse = _createPostResponse.asStateFlow()

    private val _saveOrUnsavePostResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val saveOrUnsavePostResponse = _saveOrUnsavePostResponse.asStateFlow()

    //response of getting saved post list
    private val _getSavedPostsResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val getSavedPostsResponse = _getSavedPostsResponse.asStateFlow()

    private var _gettingCurrentLocationResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val gettingCurrentLocationResponse = _gettingCurrentLocationResponse.asStateFlow()

    private var _location = MutableStateFlow(Location())
    val location = _location.asStateFlow()

    private var _appliedApplicationsLocallyList = MutableStateFlow<List<Entity>>(emptyList())
    val appliedApplicationsLocallyList = _appliedApplicationsLocallyList.asStateFlow()


    private var _currentProfileLocation = MutableStateFlow("")
    val currentProfileLocation = _currentProfileLocation.asStateFlow()

    init {
        if(applicationContext.hasLocationPermission()){
            getPost()
            getSavedPost()
            getCurrentLocation()
            getProfileLocationLocally()
    }
    }

    init {  //caching mechanism for applied applications
        viewModelScope.launch(Dispatchers.IO) {
        val currentUser = SharedComponents.currentUser
            if(databaseIsEmptyUseCase.invoke() && currentUser!=null){
                addAppliedApplicationLocallyUseCase.invoke(Entity("dummy"))
                val result =  getAppliedApplicationsUseCase.invokeToGetAppliedApplicationsRemotely(currentUser.uid)
                if(result.first.isNotEmpty()){
                    result.first.forEach{
                        addAppliedApplicationLocallyUseCase.invoke(Entity("${it.authorId}_${it.applicantId}_${it.postTimeStamp}"))
                    }
                }
            }
            getAppliedApplications()
        }
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



    fun createPost(post: Post){
        viewModelScope.launch {
          _createPostResponse.value =  postCreationUseCase.invoke(post)
        }
    }

   private fun getPost(){
        viewModelScope.launch(Dispatchers.IO) {
            getPostUseCase.invoke().collect{
                _post.value = it
            }
        }
    }
    fun savePost(savedPost: SavedPost){
        viewModelScope.launch {
          _saveOrUnsavePostResponse.value =  savePostUseCase.invoke(savedPost)
        }
    }

  fun getSavedPost(){
      _getSavedPostsResponse.value = Response.Loading
      viewModelScope.launch {

            _getSavedPostsResponse.value =getSavedPostUseCase.invoke()
        }
    }

    fun removeSavedPost(authorId:String,savedBy:String,timestamp: Timestamp){
        viewModelScope.launch {
          _saveOrUnsavePostResponse.value=  removeSavedPostUseCase.invoke(authorId = authorId, savedBy = savedBy, timeStamp = timestamp)
        }
    }

    fun insertAppliedApplication(uniqueIdentifier:String){
        addAppliedApplicationLocallyUseCase.invoke(entity = Entity(uniqueIdentifier))
    }

   private suspend fun getAppliedApplications(){
            getAppliedApplicationsUseCase.invokeToGetAppliedApplicationsLocally().collect{
                _appliedApplicationsLocallyList.value = it

        }
    }
    fun resetValue(){
        _createPostResponse.value = Response.Success(false)
        _saveOrUnsavePostResponse.value = Response.Loading
    }

   private fun getProfileLocationLocally(){
        viewModelScope.launch {
            userDatastore.getCurrentProfileLocation().collect{
                _currentProfileLocation.value = it?:""
            }
        }
    }


}