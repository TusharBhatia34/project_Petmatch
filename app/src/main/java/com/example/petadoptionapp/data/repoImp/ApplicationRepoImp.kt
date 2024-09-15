package com.example.petadoptionapp.data.repoImp

import com.example.petadoptionapp.data.common.Collections
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.model.Applications
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.repo.ApplicationRepo
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class ApplicationRepoImp:ApplicationRepo {
    private val db = Firebase.firestore
    override suspend fun sendApplication(
        answers: List<String>,
        authorId: String,
        applicantId: String,
        postTimeStamp: Timestamp,
        applicantProfilePicture: String,
        applicantUserName:String,
        petName:String,
        timestamp: Timestamp
    ): Response<Boolean> {

        try {
            db.collection(Collections.APPLICATIONS)
                .document("${applicantId}_${authorId}_${timestamp.toDate()}_${timestamp.toDate().time}")
                .set(Applications(
                   applicantId =  applicantId,
                    applicationAnswers =  answers,
                    authorId =  authorId,
                    postTimeStamp = postTimeStamp,
                    timestamp = timestamp,
                    hasRead = false,
                    applicantUserName = applicantUserName ,
                    petName = petName,
                    applicantProfilePicture = applicantProfilePicture,
                    documentId = "${applicantId}_${authorId}_${timestamp.toDate()}_${timestamp.toDate().time}"
                )).await()
            return Response.Success(true)
        }
        catch (e:Exception){
            return Response.Failure(e)
        }

    }


    override suspend fun getApplications(): Pair<List<Applications>,Response<Boolean>> {
        try{
            val allApplications = mutableListOf<Applications>()
            db.collection(Collections.APPLICATIONS)
                .whereEqualTo("authorId",SharedComponents.currentUser!!.uid)
                .get()
                .addOnSuccessListener { snapshot->
                    for (document in snapshot){
                        val application = document.toObject<Applications>()
                        allApplications.add(application)
                    }
                }.await()

            return Pair(allApplications.ifEmpty { emptyList() },Response.Success(true))
        }
        catch(e:Exception){
return Pair(emptyList(),Response.Failure(e))
        }
    }

    override suspend fun getApplicationPost(
        authorId: String,
        timestamp: Timestamp,
    ): Pair<Post?, Response<Boolean>> {
        try{

            val post  = db.collection(Collections.POSTS)
                .whereEqualTo("authorId",authorId)
                .whereEqualTo("timestamp",timestamp)
                .get()
                .await()
         return Pair(if(!post.isEmpty) post.documents[0].toObject<Post>() else null,Response.Success(true))
        }
        catch (e:Exception){
return Pair(null,Response.Failure(e))
        }
    }

   override suspend fun editNotification(applicantId:String,authorId:String,timestamp:Timestamp){
        db.collection(Collections.APPLICATIONS)
            .document("${applicantId}_${authorId}_${timestamp.toDate()}_${timestamp.toDate().time}")
            .set(hashMapOf("hasRead" to true), SetOptions.merge())
    }

    override suspend fun setApplicationStatus(
       documentId: String,
        status: String
    ): Response<Boolean> {
        try {
 db.collection(Collections.APPLICATIONS)
  .document(documentId)
     .set(hashMapOf("applicationStatus" to status), SetOptions.merge())
     .await()
           return if(status=="Accepted") Response.Success(true) else Response.Success(false)


        }
        catch (e:Exception){
            return Response.Failure(e)
        }
    }
    override suspend fun getAppliedApplications(applicantId: String): Pair<List<Applications>, Response<Boolean>> {
        try{
            val allApplications = mutableListOf<Applications>()
            val documents =  db.collection(Collections.APPLICATIONS)
                .whereEqualTo("applicantId",applicantId)
                .get()
                .await()
            if (!documents.isEmpty){
                for (document in documents){
                    val application = document.toObject<Applications>()
                    allApplications.add(application)
                }
            }
            return Pair(allApplications.ifEmpty { emptyList() },Response.Success(true))
        }
        catch (e:Exception){
            return Pair(emptyList(),Response.Failure(e))
        }
    }
}