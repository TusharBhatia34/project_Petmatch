package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.presentation.components.EmptyListScreen
import com.example.petadoptionapp.presentation.viewModels.ApplicationViewModel
import com.example.petadoptionapp.ui.theme.AppTheme
import com.example.petadoptionapp.ui.theme.quickSand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    navController: NavController,
    applicationViewModel: ApplicationViewModel = hiltViewModel()
) {
    val applicationsList =applicationViewModel.applicationList.collectAsStateWithLifecycle()
    val response = applicationViewModel.getApplicationResponse.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Notifications",
                    fontWeight = FontWeight.Bold,
                    fontFamily = quickSand,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)) },


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

                                Row (modifier = Modifier
                                    .fillMaxWidth()

                                    .clickable {
                                        SharedComponents.timeStamp = applicationsList.value[index].postTimeStamp
                                        applicationViewModel.toUpdateHasRead(
                                            applicationsList.value[index].applicantId,
                                            applicationsList.value[index].authorId,
                                            applicationsList.value[index].timestamp,
                                            index
                                        )

                                        navController.navigate(
                                            Routes.ApplicationFormRoute(
                                                applicationsList.value[index].applicationAnswers,
                                                applicationsList.value[index].authorId,
                                                applicationsList.value[index].applicationStatus,
                                                applicationsList.value[index].documentId,
                                                index
                                            )
                                        )
                                    },
                                  ){


                                    if(applicationsList.value[index].applicantProfilePicture == "") {
                                        Image(painter = painterResource(id = R.drawable.defaultpp), contentDescription = null,modifier = Modifier
                                            .clip(CircleShape)
                                            .size(50.dp))
                                      }
                                    else {
                                        SubcomposeAsyncImage(model = applicationsList.value[index].applicantProfilePicture, contentDescription = null, contentScale = ContentScale.Crop , modifier = Modifier
                                            .clip(CircleShape)
                                            .size(50.dp),
                                            loading = {
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    CircularProgressIndicator(modifier = Modifier.size(35.dp),color = MaterialTheme.colorScheme.inversePrimary)

                                                }
                                            }
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(AppTheme.dimens.medium ))
                                    Text(
                                        text = "${applicationsList.value[index].applicantUserName} has applied to adopt ${applicationsList.value[index].petName}. Review the application now!",
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .weight(1f),
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    if(!applicationsList.value[index].hasRead) {
                                        Icon(
                                            imageVector = Icons.Default.Circle,
                                            contentDescription = null ,
                                            modifier = Modifier
                                                .size(12.dp)
                                                .padding(top = 5.dp),
                                            tint = Color.Red
                                        )
                                    }


                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }

            }
        }
    }
}
