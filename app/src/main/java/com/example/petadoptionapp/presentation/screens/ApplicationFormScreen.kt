package com.example.petadoptionapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.presentation.viewModels.ApplicationViewModel
import com.example.petadoptionapp.ui.theme.AppTheme
import com.example.petadoptionapp.ui.theme.acceptApplication
import com.example.petadoptionapp.ui.theme.quickSand
import com.example.petadoptionapp.ui.theme.rejectApplication
import com.example.petadoptionapp.ui.theme.stillInReviewButtonColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationFormScreen(
    answersList: List<String>,
    navController: NavController,
    authorId: String = "",
    status:String,
    documentId:String,
    myApplicationViewScreen:Boolean = false,
    applicationViewModel: ApplicationViewModel = hiltViewModel(),
    index:Int

) {
    val response =  applicationViewModel.getApplicationPostResponse.collectAsStateWithLifecycle()
    val applicationStatusResponse = applicationViewModel.getApplicationStatusResponse.collectAsStateWithLifecycle()
    val applicationPost = applicationViewModel.getApplicationPost.collectAsStateWithLifecycle()

    applicationViewModel.toGetApplicationPost(authorId,SharedComponents.timeStamp)

    val context = LocalContext.current
    var showCircularBar by remember {
        mutableStateOf(false)
    }
    var applicationStatus by remember { mutableStateOf(status)}
Scaffold(
    topBar = {
        TopAppBar(
            title = { 
                Text(
                text = "Application",
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
        age = post.bornOn,
        gender = post.gender,
        city = post.city,
        name = post.name,
        photos = post.photos,
        description = post.description,
        breed = post.breed,
        healthInformation = post.healthInformation,
        authorId = post.authorId,
        timestamp = post.timestamp.toString(),
        type = post.type,
        country = post.country,
        state = post.state
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
                    Text(text = "View post", fontWeight = FontWeight.Bold, fontFamily = quickSand)
                }
            }

            )
    },
    bottomBar = {

        BottomAppBar(actions = {
            if(myApplicationViewScreen && applicationStatus == ""){
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Button(onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor  = stillInReviewButtonColor,
                            disabledContentColor = MaterialTheme.colorScheme.surface
                        ),shape = RoundedCornerShape(8.dp)
                        ,modifier = Modifier.fillMaxWidth(0.75f),
                        enabled = false) {
                        Text(text = "Still in review", fontWeight = FontWeight.Bold, fontFamily = quickSand)
                    }
                }
            }
            else if(applicationStatus==""){

                Row (Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        applicationViewModel.toSetApplicationStatus(documentId, "Rejected",index)

                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = rejectApplication,
                        contentColor = Color.White
                    ),shape = RoundedCornerShape(8.dp)) {
                        Text(text = "Reject", fontWeight = FontWeight.Bold, fontFamily = quickSand)
                    }
                    Button(onClick = {
                        applicationViewModel.toSetApplicationStatus(documentId, "Accepted",index)


                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = acceptApplication,
                        contentColor = Color.White
                    ),shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(text = "Accept", fontWeight = FontWeight.Bold, fontFamily = quickSand)
                    }
                }
            }
            else if(applicationStatus=="Accepted"){
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Button(onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor  = acceptApplication,
                            disabledContentColor = Color.White
                        ),shape = RoundedCornerShape(8.dp)
                        ,modifier = Modifier.fillMaxWidth(0.75f),
                        enabled = false) {
                        Text(text = "Accepted", fontWeight = FontWeight.Bold, fontFamily = quickSand)
                    }
                }
            }
            else{
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Button(onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor  = rejectApplication,
                            disabledContentColor = Color.White
                        ),shape = RoundedCornerShape(8.dp)
                        ,modifier = Modifier.fillMaxWidth(0.75f),
                        enabled = false) {
                        Text(text = "Rejected", fontWeight = FontWeight.Bold, fontFamily = quickSand)
                    }
                }
            }

        },
            containerColor = MaterialTheme.colorScheme.surface)
    }
) {
Column(modifier = Modifier

    .fillMaxSize()
    .background(MaterialTheme.colorScheme.surface)
    .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
    .padding(horizontal = AppTheme.dimens.mediumLarge)
    .verticalScroll(
        rememberScrollState()
    )) {

    Text(text = "Personal Information", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
    Text(text  = "What is your full name?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.SemiBold)
    Text(text = "Ans. "+answersList[0], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)


}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
    Text(text  = "What is your email address?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    Text(text = "Ans. "+answersList[1], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
    Text(text  = "What is your phone number?(Optional)",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    Text(text = "Ans. "+ if(answersList[2]!="")answersList[2] else "Not answered", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
}
    Spacer(modifier = Modifier.height(24.dp))

    Text(text = "Household Information", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
    Text(text  = "Do you live in a house or an apartment?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    Text(text = "Ans. "+answersList[3], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
    Text(text  = "Do you own or rent your home?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
Text(text = "Ans. "+answersList[4], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
    Text(text  = "How many people live in your household?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    Text(text = "Ans. "+answersList[5], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
}
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
    Text(text  = "Are there any children in the household? If yes, what are their ages?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        Text(text = "Ans. "+answersList[6], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
}
    Spacer(modifier = Modifier.height(24.dp))
    Text(text = "Pet Experience", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Text(text  = "Have you had pets before? If yes, please provide details (type of pet, how long you had them, etc).",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
     Text(text = "Ans. "+answersList[7], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    }
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Text(text  = "Do you currently have any pets? If yes, please provide details (type, age, etc).",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
     Text(text = "Ans. "+answersList[8], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    }

    Spacer(modifier = Modifier.height(24.dp))
    Text(text = "Pet Care", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Text(text  = "Who will be primarily responsible for the pet's care?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
     Text(text = "Ans. "+answersList[9], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    }

    Spacer(modifier = Modifier.height(24.dp))
    Text(text = "Financial", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Text(text  = "Are you financially prepared to cover pet expenses, including emergencies?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
     Text(text = "Ans. "+answersList[10], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text(text = "Others", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Text(text  = "Why are you interested in adopting this pet?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        Text(text = "Ans. "+answersList[11], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    }
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Text(text  = "Are you willing to provide updates and stay in touch with the pet's previous owner if requested?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
        Text(text = "Ans. "+answersList[12], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    }
    Spacer(modifier = Modifier.height(12.dp))

    ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Text(text  = "Is there anything else you would like us to know about you and your application?",modifier =Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
       Text(text = "Ans. "+answersList[13], modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Medium)
    }
    
}
    LaunchedEffect(applicationStatusResponse.value) {
     when(val response = applicationStatusResponse.value){
         is Response.Failure -> {
           showCircularBar = false
         }
         Response.Loading -> {}
         is Response.Success -> {
             showCircularBar = false
             applicationStatus = if(response.data){
                 "Accepted"
             } else{
                 "Rejected"
             }
         }
     }
    }
}
}






