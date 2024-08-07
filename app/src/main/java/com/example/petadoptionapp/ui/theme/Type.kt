package com.example.petadoptionapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.petadoptionapp.R

val quickSand = FontFamily(
    Font(R.font.quicksand_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.quicksand_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.quicksand_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.quicksand_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.quicksand_regular, FontWeight.Normal, FontStyle.Normal)
)

val typographySmall =Typography(
    bodyLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    displaySmall = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp
    ),
    displayMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp
    ),
    displayLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),

    )

val typographyCompact = Typography(
    bodyLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 23.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    displaySmall = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 29.sp
    ),
    displayMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Light,
        fontSize = 32.sp
    ),
    displayLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Light,
        fontSize = 35.sp
    ),
)

val typographyMedium = Typography(
    bodyLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    headlineSmall= TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 27.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp
    ),
    displayMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 35.sp
    ),
    displayLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 38.sp
    ),
)

val typographyBig = Typography(
    bodyLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 29.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Normal,
        fontSize = 29.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    displaySmall = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 42.sp
    ),
    displayMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 45.sp
    ),
    displayLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp
    ),
)