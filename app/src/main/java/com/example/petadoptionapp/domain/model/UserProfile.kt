package com.example.petadoptionapp.domain.model

data class UserProfile(
    val name:String="",
    val country:String="",
    val state:String = "",
    val city:String = "",
    val profilePicture:String="",
    val about:String="",
    val latitude:Double = 0.0,
    val longitude:Double = 0.0
)