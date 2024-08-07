package com.example.petadoptionapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.presentation.viewModels.ApplicationViewModel
import com.example.petadoptionapp.ui.theme.AppTheme
import com.example.petadoptionapp.ui.theme.quickSand


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationFormScreen(
    applicationAnswersScreen: Boolean,
    answersList: List<String>,
    navController: NavController,
    authorId: String = "",

    applicationViewModel: ApplicationViewModel = hiltViewModel(),
) {
    val response =  applicationViewModel.getApplicationPostResponse.collectAsStateWithLifecycle()
    val applicationPost = applicationViewModel.getApplicationPost.collectAsStateWithLifecycle()
    if (applicationAnswersScreen){

        applicationViewModel.toGetApplicationPost(authorId,SharedComponents.timeStamp)
    }
    val context = LocalContext.current

Scaffold(
    topBar = {
        TopAppBar(
            title = { 
                Text(
                text = "Application form",
                    fontWeight = FontWeight.Bold,
                    fontFamily = quickSand,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            ) },
            navigationIcon = {

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier =Modifier.size(30.dp)
                    )
                }

            },
            actions = {
                Button(onClick = {
                    when(response.value){
                        is Response.Failure -> {

Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                        }
                        Response.Loading -> {
                        }
                        is Response.Success -> {
                            val post = applicationPost.value
if(post!=null){
    navController.navigate(Routes.PostDetailsScreen(
        age = post.age,
        gender = post.gender,
        location = post.location,
        name = post.name,
        photos = post.photos,
        description = post.description,
        breed = post.breed,
        healthInformation = post.healthInformation,
        authorId = post.authorId,
        timestamp = post.timestamp.toString(),
        type = post.type
    ))
}
                            else {
                                navController.navigate(Routes.PostDetailsScreen())
                            }


                        }
                    }


                },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(text = "View post")
                }
            }

            )
    }
) {
Column(modifier = Modifier

    .fillMaxSize()
    .background(MaterialTheme.colorScheme.surface)
    .padding(top = it.calculateTopPadding())
    .padding(horizontal = AppTheme.dimens.mediumLarge)
    .verticalScroll(
        rememberScrollState()
    )) {

    Text(text = "Personal Information", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
    Text(text  = "What is your full name?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.SemiBold)
    if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[0], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)}


}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
    Text(text  = "What is your email address?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[1], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
    Text(text  = "What is your phone number?(Optional)",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+ if(answersList[2]!="")answersList[2] else "Not answered", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
}
    Spacer(modifier = Modifier.height(24.dp))

    Text(text = "Household Information", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
ElevatedCard(modifier = Modifier.fillMaxWidth()) {
    Text(text  = "Do you live in a house or an apartment?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[3], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
    Text(text  = "Do you own or rent your home?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[4], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
    Text(text  = "How many people live in your household?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[5], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
    Text(text  = "Are there any children in the household? If yes, what are their ages?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[6], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
}
    Spacer(modifier = Modifier.height(24.dp))
    Text(text = "Pet Experience", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Text(text  = "Have you had pets before? If yes, please provide details (type of pet, how long you had them, etc).",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[7], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
    }
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Text(text  = "Do you currently have any pets? If yes, please provide details (type, age, etc).",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[8], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
    }

    Spacer(modifier = Modifier.height(24.dp))
    Text(text = "Pet Care", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Text(text  = "Who will be primarily responsible for the pet's care?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[9], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
    }

    Spacer(modifier = Modifier.height(24.dp))
    Text(text = "Financial", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Text(text  = "Are you financially prepared to cover pet expenses, including emergencies?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[10], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text(text = "Others", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Text(text  = "Why are you interested in adopting this pet?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[11], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
    }
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Text(text  = "Are you willing to provide updates and stay in touch with the pet's previous owner if requested?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[12], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
    }
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Text(text  = "Is there anything else you would like us to know about you and your application?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        if(applicationAnswersScreen) {Text(text = "Ans. "+answersList[13], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium) }
    }
    Spacer(modifier = Modifier.height(24.dp))
}
}
}






