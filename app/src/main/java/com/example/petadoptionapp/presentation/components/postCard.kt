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
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.ui.theme.prim

@Composable
fun PostCard(
    post:Post,
    navController: NavController
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(8.dp)
            .clickable (onClick ={
                navController.navigate(
                    Routes.PostDetailsScreen(
                        age = post.age,
                        gender = post.gender,
                        location = post.location,
                        name = post.name,
                        photos = post.photos,
                        description = post.description,
                        breed = post.breed,
                        healthInformation = post.healthInformation,
                        authorId = post.authorId,
                        timestamp = post.timestamp.toString(),
                        type = post.type
                    )
                )
            })
            ,
    ) {
Column(
    modifier =Modifier.padding(8.dp).width(IntrinsicSize.Min)

) {
        AsyncImage(
            model = post.photos[0],
             contentDescription = null,
            modifier = Modifier
        .padding(bottom = 8.dp)
        .clip(RoundedCornerShape(10.dp))
        .size(170.dp),
            contentScale = ContentScale.FillBounds

)
Row (modifier = Modifier){
    Text(text = post.name)
    Spacer(modifier = Modifier.weight(1f))
    Icon(imageVector = if(post.gender == "Male")Icons.Default.Male else Icons.Default.Female,
        contentDescription = null,
        modifier = Modifier
        .height(16.dp)
        .align(Alignment.CenterVertically),
        tint =if(post.gender == "Male") Color.Blue else prim)
}
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = post.location, fontSize = 8.sp, color = Color.Gray)
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = post.age, fontSize = 8.sp, color = Color.Gray)

}

    }

}
