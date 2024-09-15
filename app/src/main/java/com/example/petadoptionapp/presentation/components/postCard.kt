package com.example.petadoptionapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.ui.theme.AppTheme
import com.example.petadoptionapp.ui.theme.male
import com.example.petadoptionapp.ui.theme.quickSand

@Composable
fun PostCard(
    post:Post,
    navController: NavController,
    isMyPostsScreen:Boolean = false
) {
    var textHeightDp by remember { mutableStateOf(0.dp) }
    val localDensity = LocalDensity.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(AppTheme.dimens.mediumLarge)
            .clickable(onClick = {
                SharedComponents.timeStamp = post.timestamp
                navController.navigate(
                    if (!isMyPostsScreen) {
                        Routes.PostDetailsScreen(
                            age = post.bornOn,
                            gender = post.gender,
                            city = post.city,
                            name = post.name,
                            photos = post.photos,
                            description = post.description,
                            breed = post.breed,
                            healthInformation = post.healthInformation,
                            authorId = post.authorId,
                            timestamp = post.timestamp.toString(),
                            type = post.type,
                            state = post.state,
                            country = post.country
                        )
                    } else {
                        Routes.MyPostDetailsScreen(
                            age = post.bornOn,
                            gender = post.gender,
                            city = post.city,
                            name = post.name,
                            photos = post.photos,
                            description = post.description,
                            breed = post.breed,
                            healthInformation = post.healthInformation,
                            authorId = post.authorId,
                            timestamp = post.timestamp.toString(),
                            type = post.type,
                            state = post.state,
                            country = post.country
                        )
                    }

                )
            }),
    ) {
Column(
    modifier = Modifier
        .padding(8.dp)
        .width(IntrinsicSize.Min)

) {
        AsyncImage(
            model = post.photos[0],
             contentDescription = null,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(10.dp))
                .size(170.dp),
            contentScale = ContentScale.Crop
)
Row (modifier = Modifier){
    Text(text = post.name, maxLines = 1,overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.SemiBold, fontFamily = quickSand)
    Spacer(modifier = Modifier.weight(1f))
    Icon(imageVector = if(post.gender == "Male")Icons.Default.Male else Icons.Default.Female,
        contentDescription = null,
        modifier = Modifier
            .height(16.dp)
            .align(Alignment.CenterVertically),
        tint =if(post.gender == "Male") male else MaterialTheme.colorScheme.primaryContainer)
}
    Spacer(modifier = Modifier.height(8.dp))
    Row {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier .size(textHeightDp)
        )
        Text(text = "${post.city}, ${post.state}, ${post.country}", fontSize = 8.sp, color = Color.Gray, maxLines = 1,overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold, fontFamily = quickSand  , modifier = Modifier

                .onGloballyPositioned {
            textHeightDp = with(localDensity) { it.size.height.toDp() }
        },)
    }
    Spacer(modifier = Modifier.height(4.dp))
    Row {
        Icon(
            imageVector = Icons.Default.Cake,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier .size(textHeightDp)
        )
        Text(text = post.bornOn, fontSize = 8.sp, color = Color.Gray, maxLines = 1,overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold, fontFamily = quickSand)
    }

}

    }

}

