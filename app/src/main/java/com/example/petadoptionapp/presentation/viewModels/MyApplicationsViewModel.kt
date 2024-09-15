package com.example.petadoptionapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.model.Applications
import com.example.petadoptionapp.domain.usecases.application.GetAppliedApplicationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyApplicationsViewModel @Inject constructor(
    private val getAppliedApplicationsUseCase: GetAppliedApplicationsUseCase

):ViewModel() {
    private val _myApplicationsList = MutableStateFlow<List<Applications>>(emptyList())
    val myApplicationList = _myApplicationsList.asStateFlow()

    private val _getAppliedApplicationsResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val getAppliedApplicationsResponse = _getAppliedApplicationsResponse.asStateFlow()

    init {
        getAppliedApplications(SharedComponents.currentUser!!.uid)
    }

 private fun getAppliedApplications(applicantId:String){
       viewModelScope.launch {
            val result = getAppliedApplicationsUseCase.invokeToGetAppliedApplicationsRemotely(applicantId)
            _myApplicationsList.value = result.first
            _getAppliedApplicationsResponse.value = result.second

        }
    }
}