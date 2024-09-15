package com.example.petadoptionapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petadoptionapp.ui.theme.AppTheme

@Composable
fun PetBackgroundContainer(
    id: Int,
    arrowIcon: Boolean = false,
    onClick:()-> Unit = {}
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    Box(
        modifier = Modifier
            .height((screenHeight / 2.5).dp)
            .background(colorScheme.primary)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart){
        if (arrowIcon)IconButton(onClick = { onClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                modifier =Modifier.size(30.dp)
            )
        }
        OvalShape(modifier = Modifier.align(Alignment.BottomCenter))

        Image(
            painter = painterResource(id = id),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(horizontal = 16.dp)
                .wrapContentSize()
                .align(Alignment.BottomCenter)
        )

    }
Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
}
@Composable
fun OvalShape(modifier: Modifier) {
    val surface = colorScheme.surface
    val primary = colorScheme.primary
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(95.dp)
        .then(modifier)
       ){
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 4
            val radiusX = size.width / 2
            val radiusY = size.height / 2

            drawRect(
                color = surface,
                topLeft = Offset(centerX-radiusX, centerY),
                size = Size(radiusX * 2, radiusY * 2),
            )
            drawArc(
                color = primary,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = true,
                topLeft = Offset(centerX - radiusX, centerY-radiusY),
                size = Size(radiusX * 2, radiusY * 2),
            )
        }
    }
}

