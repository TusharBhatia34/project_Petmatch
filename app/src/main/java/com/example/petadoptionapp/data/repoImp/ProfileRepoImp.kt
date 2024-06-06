package com.example.petadoptionapp.data.repoImp

import com.example.petadoptionapp.data.common.Collections
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.UserProfile
import com.example.petadoptionapp.domain.repo.ProfileRepo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProfileRepoImp:ProfileRepo {
    val db = Firebase.firestore
    override suspend fun editProfile() {
        TODO("Not yet implemented")
    }

    override suspend fun saveProfile(userProfile: UserProfile): Flow<Response<Boolean>> =
callbackFlow {
    try {
        db.collection(Collections.USERS)
            .document(Collections.currentUser?.uid!!)
            .set(userProfile)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    Response.Success(true)
                }
                else{
                   Response.Failure(task.exception!!)
                }
            }

        trySend(Response.Loading)
    }
    catch (e:Exception){
        trySend(Response.Failure(e))
    }
}

}