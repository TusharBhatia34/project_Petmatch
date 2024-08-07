package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.presentation.viewModels.AuthViewModel

//
@Composable
fun ProfileSettingScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    var showLogOutDialog by rememberSaveable {
        mutableStateOf(false)
    }

Column(modifier = Modifier
    .fillMaxSize()
    .background(MaterialTheme.colorScheme.surface)
    .padding(8.dp)) {
    Card (
        colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ),shape = RoundedCornerShape(8.dp), modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)

        .clickable { navController.navigate(Routes.ViewProfileScreen) }){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)){
            Text(text = "View profile",modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
            Icon(imageVector = Icons.Filled.ArrowForwardIos, contentDescription = null,tint = Color.Gray)
        }

    }
    Card (
        colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { navController.navigate(Routes.MyPostsScreen) }){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)){
            Text(text = "My Posts",modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null,tint = Color.Gray)
        }

    }
    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)){
            Text(text = "My Applications",modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null,tint = Color.Gray)
        }

    }
    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)){
            Text(text = "Delete My account",modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null,tint = Color.Gray)
        }

    }
    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                showLogOutDialog = true
            }){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)){
            Text(text = "Log Out",modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null,tint = Color.Gray)
        }

    }
    if(showLogOutDialog){

        AlertDialog(
            onDismissRequest = { showLogOutDialog = false },
            confirmButton = {
                Button(onClick = {
                    authViewModel.signOut()
                    showLogOutDialog =false


                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showLogOutDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text(text ="Confirm Logout", fontWeight = FontWeight.Bold)},
            text = {
                Text(text = "Are you sure you want to log out?")
            })
    }

}


}

