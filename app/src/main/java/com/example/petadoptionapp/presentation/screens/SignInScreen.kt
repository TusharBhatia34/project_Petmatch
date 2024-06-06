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
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
fun SignInScreen(
    navController: NavController,
    authViewModel:AuthViewModel = hiltViewModel()
) {
 
    var email by remember{mutableStateOf("")}
    var password by remember{mutableStateOf("")}
    val isUserSignedIn  =  authViewModel.isUserSignedIn.collectAsState()
    var showPassword by remember { mutableStateOf(false)}
    val interactionSource = remember { MutableInteractionSource() }
    var isClicked by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .imePadding()
            .verticalScroll(rememberScrollState())
            ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
        ) {

        PetBackgroundContainer(id = R.drawable.homescreen)
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
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp) ,
            colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  prim,
            unfocusedBorderColor = primaryDark
        ),
            placeholder = { Text(text = "Password",color = Color.Gray)},
            trailingIcon = {
                Icon(imageVector = if(showPassword) Icons.Default.VisibilityOff else Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.clickable { showPassword=!showPassword },tint = Color.Gray )
            },
            visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation()

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Forgot password?",
            Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .clickable(
                    onClick = { navController.navigate(Routes.ForgotPasswordScreen) },
                    indication = null,
                    interactionSource = interactionSource
                ),
            textAlign = TextAlign.End,
            color = prim,
           fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {

            if(email != "" && password != ""){

            authViewModel.signIn(email, password)
        }
                         },
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryDark

            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            if(!isClicked) {
                Text(text = "Sign in", color = onPrimaryDark)
            }
            else{
                CircularProgressIndicator(color = onPrimaryDark,modifier = Modifier.size(ButtonDefaults.IconSize), strokeWidth = 2.dp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.Center,){
            Text(text = "Don't have an account?"
                , textAlign = TextAlign.Center
                ,color = onPrimaryDark)

Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Create an account",
                color = prim,
                fontWeight = FontWeight.Bold,
                modifier =  Modifier.clickable {

                            navController.navigate(Routes.SignUpScreen)
                }
                )
        }
        LaunchedEffect(isUserSignedIn.value) {
            when(val response =isUserSignedIn.value){
                is Response.Failure -> {
                    isClicked =false
                    Toast.makeText(context, response.e.message, Toast.LENGTH_SHORT).show()

                }
                Response.Loading -> {
                }
                is Response.Success -> {
                    authViewModel.resetValue()
                    navController.navigate(Routes.HomeScreen){
                        popUpTo(Routes.SignInScreen){
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
    }
