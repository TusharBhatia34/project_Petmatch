package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.presentation.components.PostCard
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.ui.theme.inversePrimaryDark
import com.example.petadoptionapp.ui.theme.on
import com.example.petadoptionapp.ui.theme.onPrimaryDark
import com.example.petadoptionapp.ui.theme.tertiaryContainerDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    postViewModel: PostViewModel = hiltViewModel(),
   navController: NavController
) {
    val postList = postViewModel.post.collectAsState()
val bottomItemList = SharedComponents.NavigationItems
    var selectedIndex by remember { mutableIntStateOf(0) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
Scaffold (
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
    TopAppBar(
        title = { Text(text = "")},

        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.White
        ),
        scrollBehavior = scrollBehavior
        )
},
    bottomBar = {
NavigationBar(
    containerColor = Color.White,
    tonalElevation = 15.dp


) {
    bottomItemList.forEachIndexed{ index , item->
        NavigationBarItem(
            selected = selectedIndex == index,
            onClick = {
                 selectedIndex=index

                when (index) {
                    0 -> {
                        navController.navigate(Routes.ProfileScreen)
                    }
                    1 -> {
                        navController.navigate(Routes.ProfileScreen)
                    }
                    2 -> {
                        navController.navigate(Routes.PostScreen)
                    }
                    3 -> {
                        navController.navigate(Routes.ProfileScreen)
                    }
                    else -> {
                        navController.navigate(Routes.ProfileScreen)

                    }
                }
            },
            icon = {
                Icon(imageVector = if(index == selectedIndex)item.selectedIcon else item.unselectedIcon, contentDescription = null )
            },
            label = { Text(text = item.title , maxLines = 1, fontSize = 11.sp , fontWeight = if(selectedIndex==index) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = tertiaryContainerDark,
                unselectedTextColor = onPrimaryDark,
                selectedIconColor = inversePrimaryDark,
                unselectedIconColor = onPrimaryDark,
                indicatorColor =on


            )

        )

    }
}
    }) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(it)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),

        ) {
         items(postList.value.size){index->

             PostCard(postList.value[index],navController)
         }
        }
         LaunchedEffect(Unit){
            postViewModel.getSavedPost()
        }
    }
}
}
