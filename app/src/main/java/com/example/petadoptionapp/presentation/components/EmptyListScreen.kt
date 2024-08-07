package com.example.petadoptionapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun EmptyListScreen(imageId:Int,headline:String) {
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        
    ) {
        Image(painter = painterResource(id = imageId), contentDescription = null,modifier = Modifier.size(300.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = headline,color = MaterialTheme.colorScheme.onBackground,style = MaterialTheme.typography.headlineMedium)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = message,color = MaterialTheme.colorScheme.onBackground.copy(0.5f), style = MaterialTheme.typography.bodyMedium,modifier = Modifier.padding(24.dp))
    }
    
}