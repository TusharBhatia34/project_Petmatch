package com.example.petadoptionapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.usecases.auth.ReloadUserUseCases
import com.example.petadoptionapp.domain.usecases.auth.ResetPasswordUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignInUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignOutUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val reloadUserUseCases: ReloadUserUseCases,
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
):ViewModel() {

    private var _isUserSignedUp =MutableStateFlow<Response<Boolean>>(Response.Loading)
    var isUserSignedUp = _isUserSignedUp.asStateFlow()
    private var _isUserSignedIn =MutableStateFlow<Response<Boolean>>(Response.Loading)
    var isUserSignedIn = _isUserSignedIn.asStateFlow()
    var isEmailVerified =MutableStateFlow(false)
    var resetPasswordEmailSent =MutableStateFlow<Response<Boolean>>(Response.Loading)
    
     fun signUp(email:String, password:String) {
         viewModelScope.launch {
             val signUpResponse = signUpUseCase.invoke(email, password)
             signUpResponse.collect { response ->
                 _isUserSignedUp.value = response
             }
         }
    }

    fun signIn(email: String,password: String){
        viewModelScope.launch {
signInUseCase.invoke(email,password).collect{
    _isUserSignedIn.value = it
}
        }
    }

    fun resetValue(){
        _isUserSignedUp.value = Response.Loading
        _isUserSignedIn.value = Response.Loading
    }
    fun reloadUser(){
        viewModelScope.launch {
          isEmailVerified.value=  reloadUserUseCases.invoke()
        }
    }
    fun signOut(){
        viewModelScope.launch {
            signOutUseCase.invoke()
        }
    }

    fun resetPassword(email: String){
        viewModelScope.launch {
            resetPasswordUseCase.invoke(email).collect{
                resetPasswordEmailSent.value = it
            }
        }
    }
}