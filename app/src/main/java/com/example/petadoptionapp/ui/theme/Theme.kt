package com.example.petadoptionapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    surface = surfaceDark ,
    onBackground = disableTextColorDark,
    surfaceContainer = surfaceContainerDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    inversePrimary = inversePrimaryDark,
    onSurface = Color.White,
    tertiaryContainer = disableButtonColorDark,
    onTertiaryContainer = disableButtonTextColorDark

)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    surface = surfaceLight,
    onBackground = disableTextColorLight,
    surfaceContainer = surfaceContainerLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    inversePrimary = inversePrimaryLight,
    onSurface = Color.Black,
    tertiaryContainer = disableButtonColorLight,
    onTertiaryContainer = disableButtonTextColorLight




)

@Composable
fun PetAdoptionAppTheme(
    windowSizeClass: WindowSizeClass,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,

) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)

        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    val orientation = when{
        windowSizeClass.width.size> windowSizeClass.height.size -> Orientation.Landscape
        else -> Orientation.Portrait
    }

    val sizeThatMatters = when(orientation){
        Orientation.Portrait -> windowSizeClass.width
        Orientation.Landscape -> windowSizeClass.height
    }
    val dimensions = when(sizeThatMatters){
        is WindowSize.Small-> smallDimensions
        is WindowSize.Compact -> compactDimensions
        is WindowSize.Large -> largeDimensions
        is WindowSize.Medium -> mediumDimensions
    }

    val typography =  when(sizeThatMatters){
        is WindowSize.Small -> typographySmall
        is WindowSize.Compact -> typographyCompact
        is WindowSize.Large -> typographyBig
        is WindowSize.Medium -> typographyMedium
    }
    ProvideAppUtils(dimensions = dimensions, orientation = orientation) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }




}
object AppTheme {
    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current
        val orientation: Orientation
        @Composable
        get() = LocalOrientationMode.current
}


