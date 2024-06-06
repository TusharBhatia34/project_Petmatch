package com.example.petadoptionapp.domain.model

data class UserProfile(
    val username:String,
    val country:String,
    val state:String,
    val city:String,
    val profilePicture:String,
    val authorId:String,
    val description:String
)