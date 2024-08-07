package com.example.petadoptionapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun ImageSlider(
    images:List<String>,
    onClick :()->Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val pagerState = rememberPagerState(
        pageCount = { images.size },
        initialPage = 0
    )
    Column (
        modifier = Modifier

    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight / 2.3).dp)
        ){
            HorizontalPager(
                state = pagerState,
                key = {images[it]},
                pageSpacing = 8.dp,
                modifier =Modifier.fillMaxHeight()
            ) {index->
                SubcomposeAsyncImage(model = images[index],
                    contentDescription = null,

                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))

                    .fillMaxHeight(),
loading = {
    Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.inversePrimary, strokeWidth = 4.dp)

    }
}
                    )
            }
            IconButton(onClick =  onClick) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
            }

        }
        Row(
            modifier =Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            images.forEach{
                ImageIndicator(active = it == images[pagerState.currentPage])
            }
        }
    }

    
}

@Composable
fun ImageIndicator(active:Boolean) {
Box(
    modifier = Modifier
        .padding(horizontal = 5.dp, vertical = 8.dp)
        .clip(CircleShape)
        .border(
            width = 0.5.dp,
            color = if (active) MaterialTheme.colorScheme.inversePrimary else Color.Black,
            shape = CircleShape
        )
        .size(5.dp)
        .background(if (active) MaterialTheme.colorScheme.inversePrimary else Color.White)

        )



}
