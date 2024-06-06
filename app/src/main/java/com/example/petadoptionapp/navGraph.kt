package com.example.petadoptionapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.petadoptionapp.data.common.Collections.currentUser
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.presentation.screens.EmailVerificationScreen
import com.example.petadoptionapp.presentation.screens.ForgotPasswordScreen
import com.example.petadoptionapp.presentation.screens.HomeScreen
import com.example.petadoptionapp.presentation.screens.PostDetailsScreen
import com.example.petadoptionapp.presentation.screens.PostScreen
import com.example.petadoptionapp.presentation.screens.ProfileScreen
import com.example.petadoptionapp.presentation.screens.SignInScreen
import com.example.petadoptionapp.presentation.screens.SignUpScreen

@Composable
fun NavGraph(navController: NavHostController) {

NavHost(navController = navController, startDestination = if(currentUser!=null)Routes.HomeScreen else Routes.SignInScreen){


    composable<Routes.SignInScreen>{
        SignInScreen(navController = navController)
    }

    composable<Routes.SignUpScreen>{
        SignUpScreen(navController = navController)
    }


    composable<Routes.HomeScreen>{
        HomeScreen(navController= navController)
    }
    composable<Routes.PostScreen>{
        PostScreen(navController)
    }
    composable<Routes.EmailVerificationRoute>{
        val email = it.toRoute<Routes.EmailVerificationRoute>().email
            EmailVerificationScreen(email,navController)
    }
    composable<Routes.ForgotPasswordScreen>{
ForgotPasswordScreen(navController = navController)
    }
    composable<Routes.PostDetailsScreen> {
val post = Post(
    age = it.toRoute<Routes.PostDetailsScreen>().age,
    breed = it.toRoute<Routes.PostDetailsScreen>().breed,
    description = it.toRoute<Routes.PostDetailsScreen>().description,
    gender = it.toRoute<Routes.PostDetailsScreen>().gender,
    healthInformation = it.toRoute<Routes.PostDetailsScreen>().healthInformation,
    name = it.toRoute<Routes.PostDetailsScreen>().name,
    photos = it.toRoute<Routes.PostDetailsScreen>().photos,
    location = it.toRoute<Routes.PostDetailsScreen>().location,
    authorId = it.toRoute<Routes.PostDetailsScreen>().authorId,
    type = it.toRoute<Routes.PostDetailsScreen>().type,
    )
        val timeStamp:String = it.toRoute<Routes.PostDetailsScreen>().timestamp
PostDetailsScreen(post = post,navController,timeStamp)
    }
    composable<Routes.ProfileScreen>{
        ProfileScreen()
    }
}
}


