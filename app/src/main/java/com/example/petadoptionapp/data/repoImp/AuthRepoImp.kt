package com.example.petadoptionapp.data.repoImp

import com.example.petadoptionapp.data.common.Collections
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.repo.AuthRepo
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow

class AuthRepoImp: AuthRepo {
private var auth = Firebase.auth
    override suspend fun createUser(email: String, password: String): StateFlow<Response<Boolean>> {
            try{
                val response: MutableStateFlow<Response<Boolean>> = MutableStateFlow(Response.Loading)
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task->
                        if (task.isSuccessful){
                            Collections.currentUser = auth.currentUser
                            Collections.currentUser?.sendEmailVerification()

                           response.value = Response.Success(true)
                        }
                        else{
                            response.value = Response.Failure(task.exception!!)
                        }
                    }
             return response.asStateFlow()
            }
            catch (e:Exception){
               return MutableStateFlow(Response.Failure(e))
            }
        }
    override suspend fun signIn(email: String, password: String): Flow<Response<Boolean>> =
        callbackFlow {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                                trySend(Response.Success(true))


                        }
                        else{
                            try {
                                throw task.exception!!
                            }

            catch (e: FirebaseAuthInvalidUserException){
trySend(Response.Failure(Exception("User does not exist")))

            }
                            catch (e: FirebaseAuthInvalidCredentialsException){
                                trySend(Response.Failure(e))

                            }
                        }
                    }
                trySend(Response.Loading)
            }

            catch (e:Exception){
trySend(Response.Failure(e))

            }
            awaitClose {  }

        }



    override suspend fun reloadUser():Boolean {
       Collections.currentUser?.reload()
        return Collections.currentUser?.isEmailVerified!!
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun resetPassword(email: String): Flow<Response<Boolean>> =  callbackFlow {
           try {
               auth.sendPasswordResetEmail(email)
                   .addOnCompleteListener{
                       if (it.isSuccessful){
                           trySend(Response.Success(true))
                       }
                   }
               trySend(Response.Loading)
           }
           catch (e:Exception){
               trySend(Response.Failure(e))
           }
        awaitClose{}
       }
    }



