package com.example.petadoptionapp.domain.model

import com.google.firebase.Timestamp

data class SavedPost(
    val age:String ="",
    val authorId:String="",
    val breed:String="",
    val description:String="",
    val gender:String="",
    val healthInformation:String="",
    val city:String="",
    val name:String ="",
    val photos:List<String> = emptyList(),
    val postTimestamp: Timestamp = Timestamp.now(),
    val savedBy:String ="",
    val timestamp: Timestamp = Timestamp.now(),
    val type:String="",
    val country:String="",
    val state:String=""
)
