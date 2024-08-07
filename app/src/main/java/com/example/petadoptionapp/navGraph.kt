package com.example.petadoptionapp

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents.currentUser
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.presentation.screens.ApplicationFormScreen
import com.example.petadoptionapp.presentation.screens.ApplyApplicationScreen
import com.example.petadoptionapp.presentation.screens.CompleteProfileScreen
import com.example.petadoptionapp.presentation.screens.EditProfileScreen
import com.example.petadoptionapp.presentation.screens.EmailVerificationScreen
import com.example.petadoptionapp.presentation.screens.ForgotPasswordScreen
import com.example.petadoptionapp.presentation.screens.HomeScreen
import com.example.petadoptionapp.presentation.screens.MapScreen
import com.example.petadoptionapp.presentation.screens.MyPostDetailScreen
import com.example.petadoptionapp.presentation.screens.MyPostsScreen
import com.example.petadoptionapp.presentation.screens.NotificationScreen
import com.example.petadoptionapp.presentation.screens.PostDetailsScreen
import com.example.petadoptionapp.presentation.screens.PostScreen
import com.example.petadoptionapp.presentation.screens.ProfileSettingScreen
import com.example.petadoptionapp.presentation.screens.SavedPostsScreen
import com.example.petadoptionapp.presentation.screens.SignInScreen
import com.example.petadoptionapp.presentation.screens.SignUpScreen
import com.example.petadoptionapp.presentation.screens.ViewProfileScreen
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.presentation.viewModels.ProfileViewModel

/*Routes.ProfileScreenRoute("location")*/

@Composable
fun NavGraph(navController: NavHostController,activity: Activity,modifier:Modifier = Modifier) {
    val postViewModel = hiltViewModel<PostViewModel>()
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val profileExists = profileViewModel.profileExists.collectAsStateWithLifecycle()


NavHost(navController = navController,
    startDestination = if(currentUser==null || !(currentUser!!.isEmailVerified))
    Routes.SignInScreen
else if(!profileExists.value) {
        Routes.CompleteProfileScreen
    }
    else {Routes.HomeScreen}
        ){

composable<Routes.CompleteProfileScreen> {
CompleteProfileScreen(activity = activity, navController = navController)
}
    composable<Routes.SignInScreen>{
        SignInScreen(navController = navController)
    }

    composable<Routes.SignUpScreen>{
        SignUpScreen(navController = navController)
    }

    composable<Routes.ViewProfileScreen>{
        ViewProfileScreen(navController = navController, profileViewModel = profileViewModel)
    }
    composable<Routes.EditProfileScreen>{
        EditProfileScreen(navController = navController, profileViewModel = profileViewModel)
    }


    composable<Routes.HomeScreen>{
        HomeScreen(navController= navController, postViewModel = postViewModel, modifier = modifier)
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
    composable<Routes.SavedPostsScreen>{
SavedPostsScreen(navController = navController, postViewModel = postViewModel,modifier = modifier)
    }
    composable<Routes.NotificationScreen>{
NotificationScreen(navController = navController)
    }
    composable<Routes.ProfileSettingScreen>{
ProfileSettingScreen(navController)
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

PostDetailsScreen(post = if(post.name=="") null else post,navController,postViewModel)
    }

    composable<Routes.MyPostsScreen>{
        MyPostsScreen(navController = navController)
    }

    composable<Routes.MyPostDetailsScreen> {
val post = Post(
    age = it.toRoute<Routes.MyPostDetailsScreen>().age,
    breed = it.toRoute<Routes.MyPostDetailsScreen>().breed,
    description = it.toRoute<Routes.MyPostDetailsScreen>().description,
    gender = it.toRoute<Routes.MyPostDetailsScreen>().gender,
    healthInformation = it.toRoute<Routes.MyPostDetailsScreen>().healthInformation,
    name = it.toRoute<Routes.MyPostDetailsScreen>().name,
    photos = it.toRoute<Routes.MyPostDetailsScreen>().photos,
    location = it.toRoute<Routes.MyPostDetailsScreen>().location,
    authorId = it.toRoute<Routes.MyPostDetailsScreen>().authorId,
    type = it.toRoute<Routes.MyPostDetailsScreen>().type,
    )

        MyPostDetailScreen(post, navController)
    }





    composable<Routes.MapScreenRoute>{
        val lat = it.toRoute<Routes.MapScreenRoute>().lat
        val long = it.toRoute<Routes.MapScreenRoute>().long
        MapScreen(lat = lat.toDouble(), long =long.toDouble(),navController)
    }
    composable<Routes.ApplyApplicationRoute>{
        val authorId = it.toRoute<Routes.ApplyApplicationRoute>().authorId
        val petName= it.toRoute<Routes.ApplyApplicationRoute>().petName

        ApplyApplicationScreen(authorId = authorId ,navController = navController, petName = petName)
    }
    composable<Routes.ApplicationFormRoute>{
        val authorId = it.toRoute<Routes.ApplicationFormRoute>().authorId
        val answersList= it.toRoute<Routes.ApplicationFormRoute>().answersList
        val applicationAnswersScreen= it.toRoute<Routes.ApplicationFormRoute>().applicationAnswersScreen

        ApplicationFormScreen(applicationAnswersScreen = applicationAnswersScreen, answersList =answersList , navController =navController,authorId= authorId )
    }

}
}


