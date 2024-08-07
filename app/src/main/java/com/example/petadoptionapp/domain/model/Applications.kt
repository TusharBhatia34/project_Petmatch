package com.example.petadoptionapp.domain.model

import com.google.firebase.Timestamp

data class Applications(
    val applicantId:String="",
    val applicantProfilePicture:String="",
    val applicantUserName:String="",
    val applicationAnswers:List<String> = emptyList(),
    val authorId:String="",
    var hasRead:Boolean = false,
    val petName:String="",
    val postTimeStamp:Timestamp=Timestamp.now(),
    val timestamp: Timestamp= Timestamp.now(),
    )
