package com.example.petadoptionapp.domain.repo

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.Applications
import com.example.petadoptionapp.domain.model.Post
import com.google.firebase.Timestamp

interface ApplicationRepo {


    suspend fun sendApplication(answers: List<String>, authorId: String, applicantId: String, postTimeStamp: Timestamp, applicantProfilePicture: String, applicantUserName: String, petName: String,timestamp: Timestamp = Timestamp.now()): Response<Boolean>

    suspend fun getApplications():Pair<List<Applications>,Response<Boolean>>

    suspend fun getApplicationPost(authorId: String,timestamp: Timestamp):Pair<Post?,Response<Boolean>>
    suspend fun editNotification(applicantId:String,authorId:String,timestamp:Timestamp)
    suspend fun getAppliedApplications(applicantId: String):Pair<List<Applications>,Response<Boolean>>//to get all the applications user has applied.

    suspend fun setApplicationStatus(documentId:String,status:String):Response<Boolean>

}