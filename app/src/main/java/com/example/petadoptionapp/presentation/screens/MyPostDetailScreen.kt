package com.example.petadoptionapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.presentation.components.ImageSlider
import com.example.petadoptionapp.presentation.viewModels.MyPostViewModel
import com.example.petadoptionapp.ui.theme.AppTheme
import com.example.petadoptionapp.ui.theme.male
import com.example.petadoptionapp.ui.theme.quickSand

@Composable
fun MyPostDetailScreen(
    post: Post,
    navController: NavController,
    myPostViewModel: MyPostViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val localDensity = LocalDensity.current
    var nameTextHeightDp by remember { mutableStateOf(0.dp) }
    var locationTextHeightDp by remember { mutableStateOf(0.dp) }
    val deletePostResponse = myPostViewModel.deletePostResponse.collectAsStateWithLifecycle()
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
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
        Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
        Row(
            modifier =Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = post.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayMedium,
                modifier =Modifier
                    .onGloballyPositioned {
                        nameTextHeightDp = with(localDensity) { it.size.height.toDp() }
                    }
            )
            Icon(
                imageVector = if(post.gender=="Male")Icons.Default.Male else Icons.Default.Female
                , contentDescription = null,
                modifier =Modifier
                    .size(nameTextHeightDp),
                tint = if(post.gender=="Male") male else MaterialTheme.colorScheme.primaryContainer
            )


        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.medium))
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
        Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
        Row {
            Text(text = "Born on:",modifier = Modifier.width(70.dp), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(text = post.age,modifier =Modifier.alpha(0.6f), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.small))

        Row {
            Text(text = "breed:",modifier = Modifier.width(70.dp), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(text = post.breed,modifier =Modifier.alpha(0.6f), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(AppTheme.dimens.small))
        Row {
            Text(text = "health:",modifier = Modifier.width(70.dp), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(
                text = post.healthInformation,
                modifier = Modifier.alpha(0.6f),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )
        }

        Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
        Text(text = "Description", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(AppTheme.dimens.smallMedium))
        ExpandableText(text = post.description, fontSize = MaterialTheme.typography.bodyMedium.fontSize)

        Spacer(modifier = Modifier.weight(1f))


            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                Button(onClick = {
                                 navController.navigate(
                                     Routes.EditPostScreenRoute(
                                         age = post.age,
                                         gender = post.gender,
                                         location = post.location,
                                         name = post.name,
                                         photos = post.photos,
                                         description = post.description,
                                         breed = post.breed,
                                         healthInformation = post.healthInformation,
                                         authorId = post.authorId,
                                         type = post.type
                                     )
                                 )
                },
                    colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(120.dp)) {
                    Text(text = "Edit", fontFamily = quickSand, fontWeight = FontWeight.Bold)
                }
                Button(onClick = {
                                 showDialog = true
                },   colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(120.dp)) {
                    Text(text = "Delete", fontWeight = FontWeight.Bold, fontFamily = quickSand)
                }
            }
            }
    LaunchedEffect(deletePostResponse.value) {
        when(val res = deletePostResponse.value){
            is Response.Failure -> {
                Toast.makeText(context,res.e.message,Toast.LENGTH_SHORT).show()
            }
            Response.Loading -> {}
            is Response.Success -> {
                navController.navigate(Routes.MyPostsScreen){
                    popUpTo(Routes.MyPostsScreen){inclusive = true}
                }
                myPostViewModel.resetValue()
            }
        }
    }
    if(showDialog){

        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    myPostViewModel.deletePost(authorId = SharedComponents.currentUser!!.uid,SharedComponents.timeStamp)
                    showDialog =false
                },     colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )) {
                    Text("Delete",fontWeight = FontWeight.Bold,
                        fontFamily = quickSand
                    )
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false },


                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )) {
                    Text("Cancel",fontWeight = FontWeight.Bold,
                        fontFamily = quickSand)
                }
            },
            title = { Text(text ="Confirm Delete", fontWeight = FontWeight.Bold)},
            text = {
                Text(text = "Are you sure you want to delete this post?")
            })
    }


        }

