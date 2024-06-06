package com.example.petadoptionapp.presentation.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen(

) {
    var username by remember{ mutableStateOf("") }
    var country by remember{ mutableStateOf("") }
    var city by remember{ mutableStateOf("") }
    var state by remember{ mutableStateOf("") }
    var description by remember{ mutableStateOf("") }
    var profilePicture by remember{ mutableStateOf("") }


    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> profilePicture = uri.toString() }
    )

    Column(modifier = Modifier
        .background(Color.White)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = username, onValueChange = {username = it} )
        TextField(
            modifier =Modifier.clickable(
                onClick = {}
            ),
            value = country,
            onValueChange = { country = it },
            placeholder = { Text(text = "Select the country") },
            readOnly = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black
            )
        )
        TextField(value = city, onValueChange = {city = it} )
        TextField(value = description, onValueChange = {description= it} )
        TextField(value = state, onValueChange = {state = it})
        Button(onClick = {
           imagePicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text(text = "add picture")
        }
        Button(onClick = {
        }) {
            Text(text = "save")
        }
    }
}
