package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.presentation.components.EmptyListScreen
import com.example.petadoptionapp.presentation.viewModels.MyApplicationsViewModel
import com.example.petadoptionapp.ui.theme.quickSand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApplicationsScreenScreen(
    navController: NavController,
    myApplicationViewModel: MyApplicationsViewModel = hiltViewModel()
) {
    val applicationsList =myApplicationViewModel.myApplicationList.collectAsStateWithLifecycle()
    val response = myApplicationViewModel.getAppliedApplicationsResponse.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "My applications",
                    fontWeight = FontWeight.Bold,
                    fontFamily = quickSand,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)) },

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },


                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )

        }){

        Column (

            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
                .padding(it)
        ) {

            when(response.value){
                is Response.Failure -> {
                    Text(text = "Error", fontWeight = FontWeight.Bold, fontSize = 28.sp)
                }

                Response.Loading -> {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface), contentAlignment = Alignment.Center){
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 4.dp)
                    }
                }

                is Response.Success -> {

                    if(applicationsList.value.isEmpty()){
                        EmptyListScreen(imageId = R.drawable.nonotification, headline = "No Notifications Yet.")
                    }
                    else {
                        LazyColumn {
                            items(applicationsList.value.size){index->
                                Card (
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .clickable {
                                            SharedComponents.timeStamp = applicationsList.value[index].postTimeStamp

                                            navController.navigate(
                                                    Routes.ApplicationFormRoute(
                                                        applicationsList.value[index].applicationAnswers,
                                                        applicationsList.value[index].authorId,
                                                        applicationsList.value[index].applicationStatus,
                                                        applicationsList.value[index].documentId,
                                                        index,
                                                        true
                                                    )
                                            )
                                        }){
                                    Row (modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)){
                                        Text(text = applicationsList.value[index].petName,modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
                                    }

                                }
                            }
                        }
                    }
                }

            }
        }
    }
}