package com.example.petadoptionapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petadoptionapp.ui.theme.onPrimaryDark
import com.example.petadoptionapp.ui.theme.primaryDark

@Composable
fun PetBackgroundContainer(
    id: Int,
    arrowIcon: Boolean = false,
    onClick:()-> Unit = {}
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    Box(modifier = Modifier
        .height((screenHeight / 2.5).dp)
        .background(primaryDark)
        .fillMaxWidth(),
        contentAlignment = Alignment.TopStart){
        if (arrowIcon) Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null,modifier = Modifier
            .padding(16.dp)
            .clickable(
                onClick = { onClick() },
                indication = null,
                interactionSource = interactionSource
            ), tint = onPrimaryDark)

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
    Spacer(modifier = Modifier.height(16.dp))
}
@Composable
fun OvalShape(modifier: Modifier) {
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

            // Draw the circle (curved part of the lens)


            // Draw the rectangle (flat part of the lens)
            drawRect(
                color = Color.White,
                topLeft = Offset(centerX-radiusX, centerY),
                size = Size(radiusX * 2, radiusY*2),
            )
            drawArc(
                color = primaryDark,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(centerX - radiusX, centerY-radiusY),
                size = Size(radiusX * 2, radiusY * 2),
            )
        }
    }

}