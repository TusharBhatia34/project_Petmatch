package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petadoptionapp.presentation.components.PostCard
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.ui.theme.PetAdoptionAppTheme
import com.example.petadoptionapp.ui.theme.quickSand
import com.example.petadoptionapp.ui.theme.rememberWindowSizeClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    postViewModel: PostViewModel = hiltViewModel(),
    navController: NavController,
    modifier:Modifier = Modifier
) {

    val postList = postViewModel.post.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())




    Scaffold (
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
    TopAppBar(
        title = { Row(modifier = Modifier.fillMaxWidth()) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null,modifier =Modifier.padding(top =2.dp),tint =MaterialTheme.colorScheme.onBackground)

            Text(
                text = "City,States,Country",
                fontWeight = FontWeight.Bold,
                fontFamily = quickSand,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),


            )
        }},
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor =MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        ),
        scrollBehavior = scrollBehavior
        )

    }) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(it)
            .then(modifier)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),

        ) {
         items(postList.value.size){index->

             PostCard(postList.value[index],navController)
         }
        }

    }
}

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = Devices.PIXEL_6_PRO)
@Composable
private fun hd() {
    PetAdoptionAppTheme(windowSizeClass = rememberWindowSizeClass()) {
        var locationTextHeightDp by remember { mutableStateOf(0.dp) }
        val localDensity = LocalDensity.current
        Scaffold (
            //modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Row(modifier = Modifier,horizontalArrangement = Arrangement.Start) {

//                            Icon(
//                                imageVector = Icons.Default.LocationOn,
//                                contentDescription = null,
//                                tint = MaterialTheme.colorScheme.onSurface,
//                                modifier = Modifier .size(locationTextHeightDp)
//                            )
                            Text(
                                text = "Indore,Madhya Pradesh,India",
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier

                                    .onGloballyPositioned {
                                        locationTextHeightDp = with(localDensity) { it.size.height.toDp() }
                                    },
                            )


                    }},
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        scrolledContainerColor = Color.White
                    )
                )

            }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(it)

            ) {


            }
            }
            }
            }



