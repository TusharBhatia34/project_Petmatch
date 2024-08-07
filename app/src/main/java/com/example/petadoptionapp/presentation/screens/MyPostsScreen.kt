package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.presentation.components.PostCard
import com.example.petadoptionapp.presentation.viewModels.MyPostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPostsScreen(
    myPostViewModel: MyPostViewModel = hiltViewModel(),
    navController: NavController
) {
    val getMyPostsResponse = myPostViewModel.getMyPostsResponse.collectAsStateWithLifecycle()
    val getMyPostsList = myPostViewModel.getMyPostsList.collectAsStateWithLifecycle()
    var editOrDeletePost by rememberSaveable {
        mutableStateOf(true)
    }


    val result = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>("hasEditedOrDeleted")


    result?.let {


        editOrDeletePost = true
        navController.currentBackStackEntry?.savedStateHandle?.remove<Boolean>("hasEditedOrDeleted")

    }
  
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "My Posts",fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
            )
        }){

        when(getMyPostsResponse.value){
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
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(it)){

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                    ) {
                        items(getMyPostsList.value.size){ index->
                            PostCard(getMyPostsList.value[index],navController, isMyPostsScreen = true)

                        }
                    }
                }
            }
        }
        LaunchedEffect(editOrDeletePost) {
if(editOrDeletePost){

    myPostViewModel.getMyPosts()
    editOrDeletePost = false
}
        }

    }



}