package com.example.petadoptionapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.presentation.components.PetBackgroundContainer
import com.example.petadoptionapp.presentation.viewModels.AuthViewModel
import com.example.petadoptionapp.ui.theme.quickSand
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun EmailVerificationScreen(
    email:String,
    navController: NavController ,
    viewModel: AuthViewModel = hiltViewModel(),
    ) {

    val isEmailVerified = viewModel.isEmailVerified.collectAsState()
    val resendVerificationEmailResponse=viewModel.resendVerificationEmailResponse.collectAsState()
    val context = LocalContext.current
    val interactionSource by remember {mutableStateOf(MutableInteractionSource()) }
    var timeLeft by rememberSaveable { mutableIntStateOf(60) }
    var timerActive by rememberSaveable { mutableStateOf(true) }
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format(Locale.US,"%02d:%02d", minutes, seconds)
    LaunchedEffect(timerActive) {
        if (timerActive) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft -= 1
            }
            timerActive = false
            timeLeft = 60
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){


        PetBackgroundContainer(id = R.drawable.emailverification)


Text(
    text = "Verify your email address",
    style = MaterialTheme.typography.headlineLarge
)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle()) {
                    append("Please click the link to verify your email address, we've sent it to ")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f))) {
                    append("$email. ")
                }




            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )


      Button(onClick = {
         viewModel.reloadUser()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
          modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
          shape = RoundedCornerShape(8.dp)
      ) {
            Text(text = "Continue",color = MaterialTheme.colorScheme.onPrimary, fontFamily = quickSand, fontWeight =  FontWeight.Bold)
        }
        Spacer(modifier = Modifier.weight(1f))
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.Center,){
            Text(text = "Didn't get the email?"
                , textAlign = TextAlign.Center
                ,color = MaterialTheme.colorScheme.onPrimary,
                 style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.width(4.dp))
            Text(text = if(!timerActive)"Resend" else "Resend in $formattedTime" ,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.bodyLarge,
                modifier =  Modifier.clickable(
                    onClick = {
                        if(!timerActive){
                            viewModel.resendVerificationEmail()
                        }

                    },
                    indication = null,
                    interactionSource = interactionSource
                )
            )
        }
    }
    LaunchedEffect(isEmailVerified.value) {
when(val response =isEmailVerified.value){
    is Response.Failure -> {
        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show()
    }
    Response.Loading -> {}
    is Response.Success -> {

        if(response.data){
            viewModel.signOut()
            navController.navigate(Routes.SignInScreen){
                popUpTo(Routes.SignUpScreen){
                    inclusive = true
                }
            }
        }
        else {
            Toast.makeText(context, "Email is not verified! Please try again.", Toast.LENGTH_SHORT).show()
        }

    }
}
    }
    LaunchedEffect(resendVerificationEmailResponse.value) {
when(resendVerificationEmailResponse.value){
    is Response.Failure -> {
        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show()
    }
    Response.Loading -> {}
    is Response.Success -> {

        Toast.makeText(context, "Email has been sent successfully!", Toast.LENGTH_SHORT).show()
        timerActive = true
        viewModel.resetResendVerificationEmailResponse()

    }
        }
    }


}






