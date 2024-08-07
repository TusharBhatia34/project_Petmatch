package com.example.petadoptionapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.usecases.auth.DeleteNotVerifiedUserUseCase
import com.example.petadoptionapp.domain.usecases.auth.ReloadUserUseCases
import com.example.petadoptionapp.domain.usecases.auth.ResendVerificationEmailUseCase
import com.example.petadoptionapp.domain.usecases.auth.ResetPasswordUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignInUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignOutUseCase
import com.example.petadoptionapp.domain.usecases.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val deleteNotVerifiedUserUseCase: DeleteNotVerifiedUserUseCase,
    private val resendVerificationEmailUseCase: ResendVerificationEmailUseCase
):ViewModel() {

    private var _isUserSignedUp =MutableStateFlow<Response<Boolean>>(Response.Loading)
    var isUserSignedUp = _isUserSignedUp.asStateFlow()
    private var _isUserSignedIn =MutableStateFlow<Response<Boolean>>(Response.Loading)
    var isUserSignedIn = _isUserSignedIn.asStateFlow()
   private var _isEmailVerified =MutableStateFlow<Response<Boolean>>(Response.Loading)
    var isEmailVerified = _isEmailVerified.asStateFlow()
    var resetPasswordEmailSent =MutableStateFlow<Response<Boolean>>(Response.Loading)

    private var _notVerifiedUserDeletedResponse =MutableStateFlow<Response<Boolean>>(Response.Loading)
    var notVerifiedUserDeletedResponse = _notVerifiedUserDeletedResponse.asStateFlow()
    private var _resendVerificationEmailResponse =MutableStateFlow<Response<Boolean>>(Response.Loading)
    var resendVerificationEmailResponse = _resendVerificationEmailResponse.asStateFlow()

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

    fun resendVerificationEmail(){
        viewModelScope.launch {
            _resendVerificationEmailResponse.value = resendVerificationEmailUseCase.invoke()
        }
    }
    fun resetValue(){
        _isUserSignedUp.value = Response.Loading
        _isUserSignedIn.value = Response.Loading
        _notVerifiedUserDeletedResponse.value = Response.Loading
    }
    fun resetResendVerificationEmailResponse(){
        _resendVerificationEmailResponse.value = Response.Loading
    }
    fun reloadUser(){
        viewModelScope.launch {
          _isEmailVerified.value = reloadUserUseCases.invoke()
        }
    }
    fun signOut(){
        viewModelScope.launch(Dispatchers.IO) {
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

    fun deleteNotVerifiedUser(){
        viewModelScope.launch {
            _notVerifiedUserDeletedResponse.value = deleteNotVerifiedUserUseCase.invoke()
        }
    }
}