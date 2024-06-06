package com.example.petadoptionapp.data.common

import kotlinx.serialization.Serializable

object Routes {

    @Serializable
    object SignInScreen

    @Serializable
    object SignUpScreen

    @Serializable
    object ForgotPasswordScreen

    @Serializable
    data class EmailVerificationRoute(val email: String)
    @Serializable
    object PostScreen
    @Serializable
    object HomeScreen
    @Serializable
    object ProfileScreen

    @Serializable
    data class PostDetailsScreen(
        val age:String,
        val breed:String,
        val description:String,
        val gender:String,
        val healthInformation:String,
        val location:String,
        val photos:List<String>,
        val name:String,
        val authorId:String,
        val timestamp: String,
        val type:String
    )


}