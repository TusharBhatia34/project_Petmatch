package com.example.petadoptionapp.data.repoImp

import com.example.petadoptionapp.data.common.Collections
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.data.local.cachingAppliedApplications.Database
import com.example.petadoptionapp.data.local.cachingUserProfile.UserDatastore
import com.example.petadoptionapp.domain.repo.AuthRepo
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImp @Inject constructor(
    private val roomDb:Database,
    private val userDatastore: UserDatastore
): AuthRepo {
    private var auth = Firebase.auth
    private  val db = com.google.firebase.Firebase.firestore
    private val storage = com.google.firebase.Firebase.storage
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
                    trySend(Response.Failure(Exception("User does not exist")))

            }
                            catch (e: FirebaseAuthInvalidCredentialsException){
                             trySend(Response.Failure(e))

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
        roomDb.clearAllTables()
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

    override suspend fun deleteUserAccount(authorId:String) {
        try {
            auth.currentUser!!.delete().await()
            deleteEveryPost(authorId)
            SharedComponents.currentUser = null
            userDatastore.clearDataStore()
            roomDb.clearAllTables()
        }
        catch (_:Exception){
            println("error occur.")
        }
    }
    private  fun deleteEveryPost(authorId: String){

            db.collection(Collections.POSTS)
                .whereEqualTo("authorId",authorId)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        for(doc in it.result.documents){
                            db.collection(Collections.POSTS)
                                .document(doc.id)
                                .delete()
                        }

                deleteEverySavedPost(authorId)
                deleteEveryPhotos(authorId)

                    }

                }
    }
    private  fun deleteEverySavedPost(authorId: String){

        db.collection(Collections.SAVED_POST)
            .whereEqualTo("authorId",authorId)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){

                    for(doc in it.result.documents){

                        db.collection(Collections.SAVED_POST)
                            .document(doc.id)
                            .delete()
                    }

                db.collection(Collections.USERS)
                    .document(authorId)
                    .delete()

                }
            }
        db.collection(Collections.SAVED_POST)
            .whereEqualTo("savedBy",authorId)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(doc in it.result.documents){
                        db.collection(Collections.SAVED_POST)
                            .document(doc.id)
                            .delete()
                    }
                }
            }


    }
    private  fun deleteEveryPhotos(authorId: String,maxTry:Int = 0){
        if(maxTry == 2){
            return
        }

        val ref = storage.reference.child("images/${authorId}")
        ref.listAll().addOnSuccessListener {
            for(item in it.items){
                item.delete()
            }
        }
            .addOnFailureListener{
                    deleteEveryPhotos(authorId,maxTry+1)}
    }

}




