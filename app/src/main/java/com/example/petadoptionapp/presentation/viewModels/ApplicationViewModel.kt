package com.example.petadoptionapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.local.cachingAppliedApplications.Entity
import com.example.petadoptionapp.domain.model.Applications
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.usecases.application.AddAppliedApplicationLocallyUseCase
import com.example.petadoptionapp.domain.usecases.application.EditNotificationUseCase
import com.example.petadoptionapp.domain.usecases.application.GetApplicationPostUseCase
import com.example.petadoptionapp.domain.usecases.application.GetApplicationsUseCase
import com.example.petadoptionapp.domain.usecases.application.SendingApplicationUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val sendingApplicationUseCase: SendingApplicationUseCase,
    private val getApplicationsUseCase: GetApplicationsUseCase,
    private val getApplicationPostUseCase: GetApplicationPostUseCase,
    private val editNotificationUseCase: EditNotificationUseCase,
    private val addAppliedApplicationLocallyUseCase: AddAppliedApplicationLocallyUseCase,
    ): ViewModel()  {

    private val _sendingApplicationResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val sendingApplicationResponse = _sendingApplicationResponse.asStateFlow()
    private val _getApplicationsResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val getApplicationResponse = _getApplicationsResponse.asStateFlow()
    private val _applicationsList = MutableStateFlow<List<Applications>>(emptyList())
    val applicationList = _applicationsList.asStateFlow()

    private val _getApplicationPostResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val getApplicationPostResponse = _getApplicationPostResponse.asStateFlow()
    private val _getApplicationPost = MutableStateFlow<Post?>(null)
    val getApplicationPost = _getApplicationPost.asStateFlow()

    init {
        getApplications()
    }
    fun sendApplication(
        answers: List<String>,
        authorId: String,
        applicantId: String,
        postTimeStamp: Timestamp,
        applicantProfilePicture: String,
        applicantUserName:String,
        petName:String
    ){
        viewModelScope.launch {

           _sendingApplicationResponse.value = sendingApplicationUseCase.invoke(
               answers,
               authorId,
               applicantId,
               postTimeStamp,
               applicantProfilePicture,
               applicantUserName,
               petName
           )
        }
    }

    private fun getApplications(){
        viewModelScope.launch {
        val result =getApplicationsUseCase.invoke()
            _applicationsList.value = result.first
            _getApplicationsResponse.value = result.second
        }
    }

    fun toUpdateHasRead(applicantId:String,authorId:String,timestamp:Timestamp,index:Int){
        val updatedList = _applicationsList.value.toMutableList()
        updatedList[index] = updatedList[index].copy(hasRead = true)
        _applicationsList.value = updatedList
        viewModelScope.launch {
            editNotificationUseCase.invoke(applicantId,authorId,timestamp)
        }
    }

    fun resetValue(){
        _sendingApplicationResponse.value = Response.Loading

    }
    fun toGetApplicationPost(authorId: String,timestamp: Timestamp){
        viewModelScope.launch {

           val result =  getApplicationPostUseCase.invoke(authorId,timestamp)
            _getApplicationPost.value = result.first
            _getApplicationPostResponse.value =  result.second
        }
    }
    fun insertAppliedApplication(uniqueIdentifier:String){
        viewModelScope.launch(Dispatchers.IO) {
            addAppliedApplicationLocallyUseCase.invoke(entity = Entity(uniqueIdentifier))
        }
    }
}