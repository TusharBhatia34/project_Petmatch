package com.example.petadoptionapp.domain.model

import com.google.firebase.Timestamp


data class Post(
    val bornOn:String ="",
    val authorId:String="",
    val breed:String="",
    val description:String="",
    val gender:String="",
    val healthInformation:String="",
    val state:String="",
    val city:String ="",
    val country:String = "",
    val photos:List<String> = emptyList(),
    val type:String="",
    val name:String ="",
    val timestamp:Timestamp = Timestamp.now(),
    )

