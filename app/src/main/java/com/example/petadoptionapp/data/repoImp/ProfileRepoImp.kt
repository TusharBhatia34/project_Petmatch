package com.example.petadoptionapp.data.repoImp


import androidx.core.net.toUri
import com.example.petadoptionapp.data.common.Collections
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.data.local.cachingUserProfile.UserDatastore
import com.example.petadoptionapp.domain.model.UserProfile
import com.example.petadoptionapp.domain.repo.ProfileRepo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ProfileRepoImp @Inject constructor(
    private val userDatastore: UserDatastore

): ProfileRepo {
    private val db = Firebase.firestore

    private val storage = Firebase.storage


    override suspend fun editProfile() {

    }

    override suspend fun saveProfile(userProfile: UserProfile,sameImage:Boolean): Response<Boolean> {
        try {
            var imageUrl = userProfile.profilePicture


            if(!sameImage){

              imageUrl = uploadProfilePicture(userProfile.profilePicture)
                          }


            val profile = userProfile.copy(profilePicture = imageUrl)
          db.collection(Collections.USERS)
                .document(SharedComponents.currentUser!!.uid)
                .set(profile)
              .await()


            userDatastore.saveProfileInfo(profile)
            return Response.Success(true) // profile saved successfully
        }
             catch (e:Exception){
            return Response.Failure(e)
        }

    }

    override suspend fun userProfile(): Response<Boolean> {
       try {
       val  result = db.collection(Collections.USERS)
               .document(SharedComponents.currentUser!!.uid)
               .get()
               .await()
           if(result.exists()){

              val userProfile = result.toObject<UserProfile>()
               userDatastore.saveProfileInfo(userProfile!!)
               return Response.Success(true)
           }
           userDatastore.updateProfileExists()
         return Response.Success(false)
       }
       catch (e:Exception){
           return Response.Failure(e)
       }
    }


    private suspend fun uploadProfilePicture(imageUrl:String):String{
        val ref = storage.reference.child("images/${SharedComponents.currentUser!!.uid}/ProfilePicture")
        ref.putFile(imageUrl.toUri()).await()
        return ref.downloadUrl.await().toString()
    }
}


