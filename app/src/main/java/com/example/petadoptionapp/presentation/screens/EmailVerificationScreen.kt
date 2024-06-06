package com.example.petadoptionapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.presentation.components.PetBackgroundContainer
import com.example.petadoptionapp.presentation.viewModels.AuthViewModel
import com.example.petadoptionapp.ui.theme.onPrimaryDark
import com.example.petadoptionapp.ui.theme.prim
import com.example.petadoptionapp.ui.theme.primaryDark

@Composable
fun EmailVerificationScreen(
   email:String,
    navController: NavController ,
    viewModel: AuthViewModel = hiltViewModel(),
    ) {

    val userVerification = viewModel.isEmailVerified.collectAsState()
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
//        LaunchedEffect(userVerification){

        PetBackgroundContainer(id = R.drawable.emailverification)
            if (userVerification.value){
                Toast.makeText(context,"Login Successfully",Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.HomeScreen)
            }
        Spacer(modifier = Modifier.height(16.dp))

Text(
    text = "Verify your email address",
    color = onPrimaryDark,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp
)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "We've sent a verification email to ",
            color = onPrimaryDark,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .alpha(0.5f)
        )
Row {
    Text(
        text = "${email}. ",
        color = prim,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(start = 16.dp)
            .alpha(0.9f)

    )
    Text(
        text = "Please click",
        color = onPrimaryDark,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(end = 16.dp)
            .alpha(0.5f)
    )
}

        Text(
            text = "link in the email to verify your email address",
            color = onPrimaryDark,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .alpha(0.5f)
        )


      Button(onClick = {
         viewModel.reloadUser()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = primaryDark,
        ),
          modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp)) {
            Text(text = "Continue",color = onPrimaryDark)
        }
        Row (modifier =Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,){
            Text(text = "Didn't get the email?"
                , textAlign = TextAlign.Center
                ,color = onPrimaryDark)

            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Resend",
                color = prim,
                fontWeight = FontWeight.Bold,
                modifier =  Modifier.clickable(
                    onClick = {}
                )
            )
        }
    }
}
