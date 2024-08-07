package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.presentation.components.EmptyListScreen
import com.example.petadoptionapp.presentation.components.PostCard
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.ui.theme.quickSand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedPostsScreen(
    postViewModel: PostViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val getSavedPostsResponse = postViewModel.getSavedPostsResponse.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Saved",
                    fontWeight = FontWeight.Bold,
                    fontFamily = quickSand,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)) },


                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                ),
            )

        }){

        when(getSavedPostsResponse.value){
            is Response.Failure -> {
                Text(text = "Error")
            }
            Response.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally


                ) {
                    CircularProgressIndicator()
                }
            }
            is Response.Success -> {
                if(SharedComponents.savedList.isEmpty()){
                    EmptyListScreen(imageId = R.drawable.nosavedpost, headline = "No Saved Posts Yet.")
                }
                else {

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(it)
                        .then(modifier)){

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                        ) {
                            items(SharedComponents.savedList.size){index->
                                val post = SharedComponents.savedList[index]
                                PostCard(Post(
                                    age = post.age,
                                    gender = post.gender,
                                    location = post.location,
                                    name = post.name,
                                    photos = post.photos,
                                    description = post.description,
                                    breed = post.breed,
                                    healthInformation = post.healthInformation,
                                    authorId = post.authorId,
                                    timestamp = post.postTimestamp,
                                    type = post.type
                                ),navController)
                            }
                        }
                    }
                }
            }
        }
    }




}

