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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.presentation.components.PetBackgroundContainer
import com.example.petadoptionapp.presentation.viewModels.AuthViewModel
import com.example.petadoptionapp.ui.theme.quickSand


@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var email by rememberSaveable{mutableStateOf("")}
    var password by rememberSaveable{mutableStateOf("")}
    var confirmPassword by rememberSaveable{mutableStateOf("")}
    var showPassword by rememberSaveable { mutableStateOf(false)}
    var showConfirmPassword by rememberSaveable { mutableStateOf(false)}
    val context = LocalContext.current
    val isUserSignedUp = authViewModel.isUserSignedUp.collectAsState()
    var isClicked by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember{ MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        PetBackgroundContainer(id = R.drawable.signup)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor =MaterialTheme.colorScheme.primary
            ),
            placeholder = { Text(text = "Email",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},
            singleLine = true,
            textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),
            shape = RoundedCornerShape(8.dp)

        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp) ,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            placeholder = { Text(text = "Password",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},
            trailingIcon = {
if(password!=""){
    Icon(imageVector = if(showPassword) Icons.Default.VisibilityOff else Icons.Filled.Visibility, contentDescription = null,
        modifier = Modifier.clickable (
            onClick = { showPassword=!showPassword },
            indication = null,
            interactionSource = interactionSource
        ), tint = Color.Gray)

} else { showPassword = false }
            },
            visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),
            shape = RoundedCornerShape(8.dp)

        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp) ,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            placeholder = { Text(text = "Confirm password",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},
            trailingIcon = {
if(confirmPassword!=""){
    Icon(imageVector = if(showConfirmPassword) Icons.Default.VisibilityOff else Icons.Filled.Visibility, contentDescription = null,
        modifier = Modifier.clickable (
            onClick = { showConfirmPassword=!showConfirmPassword },
            indication = null,
            interactionSource = interactionSource
        ),tint = Color.Gray )

} else { showConfirmPassword = false }
            },
            visualTransformation = if(showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),
            shape = RoundedCornerShape(8.dp)

        )
        Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {

                if(email==""||password==""||confirmPassword==""){
                    Toast.makeText(context,"All fields are required. Please complete all fields.",Toast.LENGTH_SHORT).show()
                }
                else if(password.length<8){
                    Toast.makeText(context,"Password must be at least 8 characters long.",Toast.LENGTH_SHORT).show()
                }
                else if(password.length>64){
                    Toast.makeText(context,"Password cannot exceed 64 characters.",Toast.LENGTH_SHORT).show()
                }
                else if(password!=confirmPassword){
                    Toast.makeText(context,"Passwords do not match",Toast.LENGTH_SHORT).show()
                }
                else {
                    authViewModel.signUp(email,password)
                    isClicked = true
                }

            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary

                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(ButtonDefaults.MinHeight),
                shape = RoundedCornerShape(8.dp)
            ) {
                    Text(text = "Sign up", color = MaterialTheme.colorScheme.onPrimary, fontFamily = quickSand, fontWeight = FontWeight.Bold)
            }

        Spacer(modifier = Modifier.weight(1f))

        Row (modifier =Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Center,) {
            Text(
                text = "Already have an account?",
                textAlign = TextAlign.Center,

style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Sign in",
                color = MaterialTheme.colorScheme.primaryContainer,

                modifier = Modifier.clickable(
                    onClick = {
                        navController.navigate(Routes.SignInScreen)
                    },
                    interactionSource = interactionSource,
                    indication = null

                ),
                style = MaterialTheme.typography.bodyLarge
            )

        }


        LaunchedEffect(isUserSignedUp.value) {
            when (val response = isUserSignedUp.value) {
                is Response.Failure -> {
                    isClicked = false
                    Toast.makeText(context, response.e.message, Toast.LENGTH_SHORT).show()
                }

                is Response.Loading -> {

                }

                is Response.Success -> {
                    isClicked = false
                    navController.navigate(Routes.EmailVerificationRoute(email))
                    authViewModel.resetValue()
                }
            }
        }
    }
    if(isClicked){
        Dialog(onDismissRequest = {  }){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 4.dp)
        }

    }

}
