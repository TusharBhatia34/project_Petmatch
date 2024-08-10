package com.example.petadoptionapp.data.repoImp

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.data.local.cachingAppliedApplications.Database
import com.example.petadoptionapp.data.local.cachingUserProfile.UserDatastore
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
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImp @Inject constructor(
    private val db:Database,
    private val userDatastore: UserDatastore
): AuthRepo {
private var auth = Firebase.auth
    override suspend fun createUser(email: String, password: String): StateFlow<Response<Boolean>> {
            try{
                val response: MutableStateFlow<Response<Boolean>> = MutableStateFlow(Response.Loading)
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task->

                        if (task.isSuccessful){
                            SharedComponents.currentUser = auth.currentUser
                            SharedComponents.currentUser?.sendEmailVerification()

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
                            SharedComponents.currentUser =  auth.currentUser
                            trySend(Response.Success(true))
                        }
                        else{
                            try {
                                throw task.exception!!
                            }

            catch (e: FirebaseAuthInvalidUserException){
Response.Failure(Exception("User does not exist"))

            }
                            catch (e: FirebaseAuthInvalidCredentialsException){
                                Response.Failure(e)

                            }
                        }
                    }

            }

            catch (e:Exception){
trySend(Response.Failure(e))

            }
            awaitClose {  }

        }



    override suspend fun reloadUser():Response<Boolean> {
        return try {
            SharedComponents.currentUser!!.reload().await()
            if(SharedComponents.currentUser!!.isEmailVerified){
                Response.Success(true)
            } else{
                Response.Success(false)
            }
        } catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
        SharedComponents.currentUser = null
        userDatastore.clearDataStore()
        db.clearAllTables()

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

    override suspend fun deleteNotVerifiedUser(): Response<Boolean> {
        try{
            SharedComponents.currentUser!!.delete().await()
            return Response.Success(true)
        }
        catch (e:Exception){
            return Response.Failure(e)
        }
    }

    override suspend fun resendVerificationEmail(): Response<Boolean> {
        return try {
          SharedComponents.currentUser!!.sendEmailVerification().await()
          Response.Success(true)
        }
        catch (e:Exception){
            Response.Failure(e)
        }

    }
}



