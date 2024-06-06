package com.example.petadoptionapp.data.common

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

object Collections {
    val POSTS = "posts"
    val USERS= "users"
    val SAVED_POST = "savedpost"
    var currentUser =Firebase.auth.currentUser

}
