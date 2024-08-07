package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.presentation.components.ImageSlider
import com.example.petadoptionapp.ui.theme.male

@Composable
fun MyPostDetailScreen(
    post:Post,
    navController: NavController,
) {
    val localDensity = LocalDensity.current
    var nameTextHeightDp by remember { mutableStateOf(0.dp) }
    var locationTextHeightDp by remember { mutableStateOf(0.dp) }
    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ImageSlider(
                images = post.photos,
                onClick = {
                    navController.previousBackStackEntry?.savedStateHandle?.set("hasEditedOrDeleted",true)
                    navController.popBackStack()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = post.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .onGloballyPositioned {
                            nameTextHeightDp = with(localDensity) { it.size.height.toDp() }
                        }
                )
                Icon(
                    imageVector = if(post.gender=="Male") Icons.Default.Male else Icons.Default.Female
                    , contentDescription = null,
                    modifier = Modifier
                        .size(nameTextHeightDp),
                    tint = if(post.gender=="Male") male else MaterialTheme.colorScheme.primaryContainer
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(imageVector = Icons.Default.LocationOn
                    , contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(end = 2.dp)
                        .alpha(0.5f)
                        .size(locationTextHeightDp)
                )
                Text(text = post.location,modifier = Modifier
                    .alpha(0.5f)
                    .onGloballyPositioned {
                        locationTextHeightDp = with(localDensity) { it.size.height.toDp() }
                    })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = "Age:",modifier = Modifier.width(60.dp), fontWeight = FontWeight.SemiBold)
                Text(text = post.age,modifier = Modifier.alpha(0.6f))
            }
            Spacer(modifier = Modifier.height(2.dp))

            Row {
                Text(text = "breed:",modifier = Modifier.width(60.dp), fontWeight = FontWeight.SemiBold)
                Text(text = post.breed,modifier = Modifier.alpha(0.6f))
            }

            Spacer(modifier = Modifier.height(2.dp))
            Row {
                Text(text = "health:",modifier = Modifier.width(60.dp), fontWeight = FontWeight.SemiBold)
                Text(text = post.healthInformation,modifier = Modifier.alpha(0.6f))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Description", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            ExpandableText(text = post.description, fontSize = 15.sp)

            Spacer(modifier = Modifier.weight(1f))


            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                Button(onClick = {  }) {
                    Text(text = "Edit")
                }
                Button(onClick = {

                }) {
                    Text(text = "Delete")
                }
            }
            }



        }

