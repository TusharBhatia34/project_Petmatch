package com.example.petadoptionapp

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.ui.theme.tertiaryContainerDark
import kotlinx.coroutines.delay

@Composable
fun BottomNavigationBar(activity:Activity) {
    val navController = rememberNavController()
    val bottomItemList = SharedComponents.NavigationItems
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    var currentRoute by rememberSaveable {
        mutableStateOf(navController.currentBackStackEntry?.destination?.route)
    }
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->

            delay(200L)
            selectedIndex = getSelectedIndexForRoute(backStackEntry.destination.route)
            currentRoute = backStackEntry.destination.route

        }

    }
    Scaffold(
        bottomBar = {

            if(currentRoute == "com.example.petadoptionapp.data.common.Routes.HomeScreen" ||
                currentRoute == "com.example.petadoptionapp.data.common.Routes.NotificationScreen" ||
                currentRoute == "com.example.petadoptionapp.data.common.Routes.SavedPostsScreen"
                || currentRoute == "com.example.petadoptionapp.data.common.Routes.ProfileSettingScreen"
            ){

                NavigationBar {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        tonalElevation = 15.dp


                    ) {
                        bottomItemList.forEachIndexed{ index , item->
                            NavigationBarItem(
                                selected = selectedIndex == index ,

                                onClick = {
                                    if(selectedIndex!=index){

                                        if(index!=2){
                                            selectedIndex=index
                                        }
                                        when (index) {
                                            0 -> {
                                                navController.navigate(Routes.HomeScreen)
                                            }
                                            1 -> {
                                            navController.navigate(Routes.SavedPostsScreen)
                                            }
                                            2 -> {
                                                navController.navigate(Routes.PostScreen)
                                            }
                                            3 -> {
                                                navController.navigate(Routes.NotificationScreen)
                                            }
                                            else -> {
                                                navController.navigate(Routes.ProfileSettingScreen)
                                            }
                                        }
                                    }
                                },
                                icon = {
                                    Icon(imageVector = if(index == selectedIndex)item.selectedIcon else item.unselectedIcon, contentDescription = null,modifier = Modifier.size(25.dp) )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedTextColor = tertiaryContainerDark,
                                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.8f),
                                    unselectedIconColor =MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f),
                                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer

                                )

                            )
                        }
                    }
                }
            }
        }
    ) {
        NavGraph(navController = navController, activity = activity,modifier =Modifier.padding(it))
    }
}

fun getSelectedIndexForRoute(route: String?): Int {
    return when (route) {
        "com.example.petadoptionapp.data.common.Routes.HomeScreen" -> 0
        "com.example.petadoptionapp.data.common.Routes.NotificationScreen" -> 3
        "com.example.petadoptionapp.data.common.Routes.PostScreen" -> 2
        "com.example.petadoptionapp.data.common.Routes.SavedPostsScreen" -> 1
        else -> 4
    }
}