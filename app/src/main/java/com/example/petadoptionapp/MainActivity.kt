package com.example.petadoptionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.petadoptionapp.ui.theme.PetAdoptionAppTheme
import com.example.petadoptionapp.ui.theme.rememberWindowSizeClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

           val window = rememberWindowSizeClass()
            PetAdoptionAppTheme(window) {
                val navController = rememberNavController()
                NavGraph(navController = navController)

                }
            }
        }
    }




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}