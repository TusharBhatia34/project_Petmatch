package com.example.petadoptionapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//different screen sizes cases and in each screen sizes , width can be compact while height be medium and also same screen size can have different
//orientation which can change their width and height value that's why we used only size variable in windowsize class

sealed class WindowSize(val size:Int){
    data class Small(val smallSize:Int):WindowSize(smallSize)
    data class Compact(val compactSize:Int):WindowSize(compactSize)
    data class Medium(val mediumSize:Int):WindowSize(mediumSize)
    data class Large(val largeSize:Int):WindowSize(largeSize)
}
//width and height of screen according to diff screen size and diff orientation
data class WindowSizeClass(val width:WindowSize,val height:WindowSize)

//to change the width and height if user goes to portrait or landscape
@Composable
fun rememberWindowSizeClass():WindowSizeClass {
    val config = LocalConfiguration.current
    val width by remember (config){
        mutableStateOf(config.screenWidthDp)
    }
    val height by remember (config){
        mutableStateOf(config.screenHeightDp)
    }
    val windowWidthClass  = when{
        width<=360 -> WindowSize.Small(width)
        width in 361..480 -> WindowSize.Compact(width)
        width in 481..720-> WindowSize.Medium(width)
        else -> WindowSize.Large(width)
    }
    val windowHeightClass  = when{
        height<=360 -> WindowSize.Small(height)
        height in 361..480 -> WindowSize.Compact(height)
        height in 481..720-> WindowSize.Medium(height)
        else -> WindowSize.Large(height)
    }
    return WindowSizeClass(windowWidthClass,windowHeightClass)
}

//orientation of screen
enum class Orientation{
    Portrait,Landscape
}
//dimensions of screen according to diff cases
data class Dimensions(
    val small:Dp,
    val smallMedium:Dp,
    val medium:Dp,
    val mediumLarge:Dp,
    val large:Dp
)

val smallDimensions = Dimensions(
    small = 1.dp,
    smallMedium = 2.dp,
    medium = 6.dp,
    mediumLarge = 12.dp,
    large = 24.dp
)
val compactDimensions = Dimensions(
    small = 2.dp,
    smallMedium = 4.dp,
    medium = 8.dp,
    mediumLarge = 16.dp,
    large = 32.dp
)

val mediumDimensions = Dimensions(
    small = 4.dp,
    smallMedium = 8.dp,
    medium = 16.dp,
    mediumLarge = 32.dp,
    large = 48.dp
)

val largeDimensions = Dimensions(
    small = 8.dp,
    smallMedium = 16.dp,
    medium = 32.dp,
    mediumLarge = 48.dp,
    large = 56.dp
)
