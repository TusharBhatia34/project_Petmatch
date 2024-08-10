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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.presentation.viewModels.ApplicationViewModel
import com.example.petadoptionapp.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyApplicationScreen(
    authorId: String,
    applicantId: String = SharedComponents.currentUser!!.uid,
    navController: NavController,
    petName:String,
    applicationViewModel: ApplicationViewModel = hiltViewModel(),
) {
    val currentProfilePicture = applicationViewModel.currentProfilePicture.collectAsStateWithLifecycle()
    val sendApplicationResponse = applicationViewModel.sendingApplicationResponse.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var isClicked by remember {
        mutableStateOf(false)
    }
    val applicantUserName = when (SharedComponents.currentUser!!.displayName){
        ""-> "unknown User"
        else -> SharedComponents.currentUser!!.displayName
    }
    val applicationAnswers = remember {
        mutableStateListOf(*List(14) { "" }.toTypedArray())
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Application form",
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.SemiBold
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

                        if(!hasEmptyAnswersExceptPhoneNumber(applicationAnswers)){
                            isClicked = true
                            applicationViewModel.sendApplication(applicationAnswers,authorId, applicantId,SharedComponents.timeStamp,currentProfilePicture.value,applicantUserName?:"",petName)
                        }
                        else {
                            Toast.makeText(context,"please answer remaining questions.",Toast.LENGTH_SHORT).show()
                        }

                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Text(text = "Apply")
                    }
                }


            )
        }
    ) { padding ->
        val questionHeadlineFontWeight = FontWeight.Bold
        val questionHeadlineStyle = MaterialTheme.typography.headlineLarge
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = padding.calculateTopPadding())
            .padding(horizontal = AppTheme.dimens.mediumLarge).fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )) {
            Text(text = "Personal Information", style = questionHeadlineStyle , fontWeight = questionHeadlineFontWeight)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text  = "1. What is your full name?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[0], onValueChange = {applicationAnswers[0]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(12.dp))


            Text(text  = "2. What is your email address?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[1], onValueChange = {applicationAnswers[1]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(12.dp))


            Text(text  = "3. What is your phone number?(Optional)",modifier = Modifier.padding(end = 8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[2], onValueChange = {applicationAnswers[2]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Household Information", style = questionHeadlineStyle , fontWeight = questionHeadlineFontWeight)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text  = "1. Do you live in a house or an apartment?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[3], onValueChange = {applicationAnswers[3]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(12.dp))

            Text(text  = "2. Do you own or rent your home?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[4], onValueChange = {applicationAnswers[4]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(12.dp))

            Text(text  = "3. How many people live in your household?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[5], onValueChange = {applicationAnswers[5]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(12.dp))

            Text(text  = "4. Are there any children in the household? If yes, what are their ages?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[6], onValueChange = {applicationAnswers[6]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Pet Experience", style = questionHeadlineStyle , fontWeight = questionHeadlineFontWeight)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text  = "1. Have you had pets before? If yes, please provide details (type of pet, how long you had them, etc).",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[7], onValueChange = {applicationAnswers[7]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(12.dp))

            Text(text  = "2. Do you currently have any pets? If yes, please provide details (type, age, etc).",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[8], onValueChange = {applicationAnswers[8]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Pet Care", style = questionHeadlineStyle , fontWeight = questionHeadlineFontWeight)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text  = "1. Who will be primarily responsible for the pet's care?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[9], onValueChange = {applicationAnswers[9]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Financial", style = questionHeadlineStyle , fontWeight = questionHeadlineFontWeight)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text  = "1. Are you financially prepared to cover pet expenses, including emergencies?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[10], onValueChange = {applicationAnswers[10]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Others", style = questionHeadlineStyle , fontWeight = questionHeadlineFontWeight)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text  = "1. Why are you interested in adopting this pet?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[11], onValueChange = {applicationAnswers[11]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(12.dp))

            Text(text  = "2. Are you willing to provide updates and stay in touch with the pet's previous owner if requested?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[12], onValueChange = {applicationAnswers[12]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(12.dp))

            Text(text  = "3. Is there anything else you would like us to know about you and your application?",modifier = Modifier.padding(end =8.dp), fontWeight = FontWeight.Medium)
            OutlinedTextField(value = applicationAnswers[13], onValueChange = {applicationAnswers[13]=it},modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.height(24.dp))

        }
        LaunchedEffect(sendApplicationResponse.value) {

            when(val response = sendApplicationResponse.value){
                is Response.Failure -> {
                    isClicked = false
                    Toast.makeText(context,response.e.message,Toast.LENGTH_SHORT).show()
                }
                Response.Loading -> {

                }
                is Response.Success -> {
                    applicationViewModel.insertAppliedApplication("${authorId}_${SharedComponents.currentUser!!.uid}_${SharedComponents.timeStamp}")
                    isClicked = false
                    navController.previousBackStackEntry?.savedStateHandle?.set("hasApplied",true)
                    navController.popBackStack()
                    Toast.makeText(context,"Application sent successfully",Toast.LENGTH_SHORT).show()

                }
            }
        }
        if(isClicked){
            Dialog(onDismissRequest = {  }){
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 4.dp)
            }

        }
    }
    }
fun hasEmptyAnswersExceptPhoneNumber(applicationsAnswers: List<String>): Boolean {
    return applicationsAnswers.indices.any { index ->
        index != 2 && applicationsAnswers[index]==""
    }
}


