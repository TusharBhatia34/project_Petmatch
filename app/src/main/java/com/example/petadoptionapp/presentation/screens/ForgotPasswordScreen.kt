package com.example.petadoptionapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.presentation.components.PetBackgroundContainer
import com.example.petadoptionapp.presentation.viewModels.AuthViewModel
import com.example.petadoptionapp.ui.theme.quickSand

@Composable
fun ForgotPasswordScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    val resetPasswordEmail = authViewModel.resetPasswordEmailSent.collectAsState()
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){

PetBackgroundContainer(id = R.drawable.forgotpassword,true, onClick = {
navController.popBackStack()
})
        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "Pet ate your password? No worries. Enter your  email address, we will send you link on email to reset the password.",
        Modifier.padding(horizontal = 32.dp).fillMaxWidth().align(Alignment.CenterHorizontally).alpha(0.5f),
            style = MaterialTheme.typography.bodyLarge,

        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            placeholder = { Text(text = "Email",color = Color.Gray, fontWeight = FontWeight.SemiBold)},
            singleLine = true,
            textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),
            shape = RoundedCornerShape(8.dp)

        )
Spacer(modifier = Modifier.height(24.dp))
    Button(onClick = {

       if  (email !="") authViewModel.resetPassword(email) else  Toast.makeText(context ,"Please enter your email address.", Toast.LENGTH_SHORT).show()
                     },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary

        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)){
        Text(text = "Send",color = MaterialTheme.colorScheme.onPrimary, fontFamily = quickSand, fontWeight =  FontWeight.Bold)
    }
        Spacer(modifier = Modifier.weight(1f))

    LaunchedEffect(resetPasswordEmail.value){
        when(resetPasswordEmail.value){
            is Response.Failure -> {
                Toast.makeText(context ,"Something went wrong.", Toast.LENGTH_SHORT).show()
            }
            Response.Loading -> {}
            is Response.Success -> {
                Toast.makeText(context ,"Email has been sent.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
}
