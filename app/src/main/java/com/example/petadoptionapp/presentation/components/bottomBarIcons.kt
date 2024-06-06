package com.example.petadoptionapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomBarIcons(
    text:String,
imageVector: ImageVector
                  ) {
Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Icon(imageVector = imageVector, contentDescription = null)
    Text(text = text)
}
                    }
