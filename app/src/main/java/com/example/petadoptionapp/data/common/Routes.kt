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
    data class MapScreenRoute(val lat:String,val long:String)
    @Serializable
    data class EmailVerificationRoute(val email: String)
    @Serializable
    data class ApplyApplicationRoute(val authorId: String,val petName:String)
    @Serializable
    data class ApplicationFormRoute(val applicationAnswersScreen: Boolean, val answersList: List<String>, val authorId: String)
    @Serializable
    object PostScreen
    @Serializable
    object HomeScreen
    @Serializable
    object SavedPostsScreen
    @Serializable
    object ViewProfileScreen
    @Serializable
    object EditProfileScreen
    @Serializable
    data class ProfileScreenRoute(val location: String)

    @Serializable
    data class PostDetailsScreen(
        val age:String ="",
        val breed:String="",
        val description:String="",
        val gender:String="",
        val healthInformation:String="",
        val location:String="",
        val photos:List<String> =emptyList(),
        val name:String="",
        val authorId:String = "",
        val timestamp: String="",
        val type:String=""
    )
    @Serializable
    object NotificationScreen
    @Serializable
    object CompleteProfileScreen
    @Serializable
    object ProfileSettingScreen
    @Serializable
    object MyPostsScreen

    @Serializable
    data class MyPostDetailsScreen(
        val age:String ="",
        val breed:String="",
        val description:String="",
        val gender:String="",
        val healthInformation:String="",
        val location:String="",
        val photos:List<String> =emptyList(),
        val name:String="",
        val authorId:String = "",
        val timestamp: String="",
        val type:String=""
    )

}