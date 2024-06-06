package com.example.petadoptionapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.presentation.components.PetBackgroundContainer
import com.example.petadoptionapp.presentation.viewModels.AuthViewModel
import com.example.petadoptionapp.ui.theme.onPrimaryDark
import com.example.petadoptionapp.ui.theme.prim
import com.example.petadoptionapp.ui.theme.primaryDark

@Composable
fun ForgotPasswordScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
) {
    var email by remember {
        mutableStateOf("")
    }
    val resetPasswordEmail = authViewModel.resetPasswordEmailSent.collectAsState()
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){

PetBackgroundContainer(id = R.drawable.forgotpassword,true, onClick = {
   navController.navigate(Routes.SignInScreen){
       popUpTo(Routes.ForgotPasswordScreen){
           inclusive=true
       }
   }
})
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Forgot Password",
            color = onPrimaryDark,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Pet ate your password? No worries. Enter your",
            color = onPrimaryDark,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .alpha(0.5f)
        )
        Text(
            text = "email address, we will send you link",
            color = onPrimaryDark,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .alpha(0.5f)
        )
        Text(
            text = "on email to reset the password.",
            color = onPrimaryDark,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .alpha(0.5f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  prim,
                unfocusedBorderColor = primaryDark
            ),
            placeholder = { Text(text = "Email",color = Color.Gray)}
        )
Spacer(modifier = Modifier.height(24.dp))
    Button(onClick = {
       if  (email !="") authViewModel.resetPassword(email)
                     },
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryDark

        ),
        modifier = Modifier.width((screenWidth/3).dp)) {
        Text(text = "Send",color = onPrimaryDark)
    }
        Spacer(modifier = Modifier.weight(1f))

    LaunchedEffect(resetPasswordEmail.value){
        when(resetPasswordEmail.value){
            is Response.Failure -> {}
            Response.Loading -> {}
            is Response.Success -> {
                Toast.makeText(context ,"Email has been sent!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
}
