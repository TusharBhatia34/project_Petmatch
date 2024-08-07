package com.example.petadoptionapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ProvideAppUtils(
    dimensions: Dimensions,
    orientation: Orientation,
    content: @Composable () -> Unit
) {
   val dimSet by remember{ mutableStateOf(dimensions) }
    val orientation by remember { mutableStateOf(orientation) }
CompositionLocalProvider (
    LocalAppDimens provides dimSet,
    LocalOrientationMode provides orientation,
    content = content
)
}
val LocalAppDimens = compositionLocalOf {
    smallDimensions
}
val LocalOrientationMode = compositionLocalOf {
    Orientation.Portrait
}