package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.presentation.components.FilterDialog
import com.example.petadoptionapp.presentation.components.PostCard
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.ui.theme.quickSand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    postViewModel: PostViewModel = hiltViewModel(),
    navController: NavController,
    modifier:Modifier = Modifier
) {

    val postList = postViewModel.post.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val currentProfileLocation = postViewModel.currentProfileLocation.collectAsStateWithLifecycle()
    var showFilterDialog by rememberSaveable{ mutableStateOf(false) }
    val currLocation = postViewModel.location.collectAsState()
    val interactionSource = remember{ MutableInteractionSource() }

    Scaffold (
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
    TopAppBar(
        title = { Row(modifier = Modifier
            .fillMaxWidth()

    .clickable(
        onClick = {
            navController.navigate(Routes.CountryListScreen(false))
        },
        indication = null,
        interactionSource = interactionSource
    )
            ) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null,modifier =Modifier.padding(top =2.dp),tint =MaterialTheme.colorScheme.onBackground)

            Text(
                text = currentProfileLocation.value.trim(),
                fontWeight = FontWeight.Bold,
                fontFamily = quickSand,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),


            )
        }},
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        actions = {
            IconButton(onClick = { showFilterDialog = true }) {
                Icon(imageVector = Icons.Default.FilterList, contentDescription = null)
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
    if(showFilterDialog){
        FilterDialog(applyAction = {  type, gender, breed, city, state, country->

            postViewModel.getFilteredPosts(type, gender, breed, city, state, country)
        },
            navController,
            currLocation = currLocation.value,
            onDismiss = {showFilterDialog =false })
    }


}





