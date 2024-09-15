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
    data class FilterPostsRoute(val screenTitle: String)
    @Serializable
    data class ApplyApplicationRoute(val authorId: String,val petName:String)
    @Serializable
    data class ApplicationFormRoute(
        val answersList: List<String> = emptyList(),
        val authorId: String="",
        val applicationStatus: String="",
        val documentId: String="",
        val index :Int= 0,
        val myApplicationViewScreen:Boolean = false,
        )
    @Serializable
    object PostScreen
    @Serializable
    object MyApplicationsScreen
    @Serializable
    object HomeScreen
    @Serializable
    object SavedPostsScreen
    @Serializable
    object ViewProfileScreen
    @Serializable
    object EditProfileScreen
    @Serializable
    data class EditPostScreenRoute(
        val age:String ="",
        val breed:String="",
        val description:String="",
        val gender:String="",
        val healthInformation:String="",
        val city:String="",
        val photos:List<String> =emptyList(),
        val name:String="",
        val authorId:String = "",
        val type:String="",
        val state: String="",
        val country:String =""
    )

    @Serializable
    data class PostDetailsScreen(
        val age:String ="",
        val breed:String="",
        val description:String="",
        val gender:String="",
        val healthInformation:String="",
        val country:String="",
        val photos:List<String> =emptyList(),
        val name:String="",
        val authorId:String = "",
        val timestamp: String="",
        val type:String="",
        val state:String ="",
        val city:String=""
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
    data class CountryListScreen(val isProfileScreen:Boolean= true)

    @Serializable
    data class MyPostDetailsScreen(
        val age:String ="",
        val breed:String="",
        val description:String="",
        val gender:String="",
        val healthInformation:String="",
        val country:String="",
        val photos:List<String> =emptyList(),
        val name:String="",
        val authorId:String = "",
        val timestamp: String="",
        val type:String="",
        val city:String="",
        val state: String=""
    )

}