package com.example.petadoptionapp.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.presentation.components.PostCard
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.ui.theme.quickSand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteredResultScreen(screenTitle:String,navController: NavController,postViewModel: PostViewModel = hiltViewModel()) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val filterResult = postViewModel.filteredPosts.collectAsStateWithLifecycle()
    val response = postViewModel.filterPostResponse.collectAsStateWithLifecycle()
BackHandler {
    navController.popBackStack(Routes.HomeScreen, inclusive = false)
}

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = screenTitle,
                        fontWeight = FontWeight.Bold,
                        fontFamily = quickSand,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                        )

                },
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack(Routes.HomeScreen, inclusive = false)
                    postViewModel.resetValue()}) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null,tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                ),
                scrollBehavior = scrollBehavior
            )

        }) {

        when(response.value){
            is Response.Failure -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally


                ) {
                    Text(text = "Error",color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Response.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.inversePrimary, strokeWidth = 4.dp)
                }
            }
            is Response.Success -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(it)
                ) {
                    if(filterResult.value.isEmpty()){
                        Text(text = "No result found.", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    }
                    else{

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),

                            ) {
                            items(filterResult.value.size){index->
                                PostCard(filterResult.value[index],navController)
                            }
                        }
                    }

                }
            }
        }

    }
}