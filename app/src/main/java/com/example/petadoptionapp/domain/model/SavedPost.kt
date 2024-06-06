package com.example.petadoptionapp.domain.model

import com.google.firebase.Timestamp

data class SavedPost(
    val age:String ="",
    val authorId:String="",
    val breed:String="",
    val description:String="",
    val gender:String="",
    val healthInformation:String="",
    val location:String="",
    val photos:List<String> = emptyList(),
    val timestamp: String = Timestamp.now().toString(),
    val type:String="",
    val name:String ="",
    val savedBy:String =""
)
