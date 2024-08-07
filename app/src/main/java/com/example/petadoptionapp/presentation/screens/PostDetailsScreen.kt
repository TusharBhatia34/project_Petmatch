package com.example.petadoptionapp.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.model.SavedPost
import com.example.petadoptionapp.presentation.components.ImageSlider
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.ui.theme.AppTheme
import com.example.petadoptionapp.ui.theme.male
import com.google.firebase.Timestamp


@Composable
fun PostDetailsScreen(
    post: Post?=null,
    navController: NavController,
    postViewModel: PostViewModel = hiltViewModel(),
    ) {
    val currentUser = SharedComponents.currentUser!!.uid
    val localDensity = LocalDensity.current
    var nameTextHeightDp by remember { mutableStateOf(0.dp) }
    var locationTextHeightDp by remember { mutableStateOf(0.dp) }
    val savedList = SharedComponents.savedList
    val timestamp = SharedComponents.timeStamp
    val interactionSource  =   remember {
        MutableInteractionSource()
    }

    var saved by rememberSaveable { mutableStateOf(savedList.any { it.savedBy==SharedComponents.currentUser!!.uid && it.postTimestamp == timestamp }) }
    val initialSavedValue by rememberSaveable { mutableStateOf(saved) }
    var hasApplied by remember {
    mutableStateOf(false)
}
    
    val saveOrUnSaveResponse = postViewModel.saveOrUnsavePostResponse.collectAsStateWithLifecycle()
    val appliedApplications = postViewModel.appliedApplicationsLocallyList.collectAsStateWithLifecycle()
    val result = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>("hasApplied")
    result?.let {
        hasApplied = it
        navController.currentBackStackEntry?.savedStateHandle?.remove<Boolean>("hasApplied")
    }
    if(post == null){
Box(modifier = Modifier.fillMaxSize()){
    Text(text = "Post is deleted")
}
    }
    else{
        BackHandler {
            if(initialSavedValue != saved){
                if(!saved){
                    postViewModel.removeSavedPost(authorId = post.authorId, savedBy = currentUser,timestamp= timestamp)
                }
                else{
                    postViewModel.savePost(
                        SavedPost(
                            age = post.age,
                            gender = post.gender,
                            location = post.location,
                            name = post.name,
                            photos = post.photos,
                            description = post.description,
                            breed = post.breed,
                            healthInformation = post.healthInformation,
                            authorId = post.authorId,
                            timestamp = Timestamp.now(),
                            type = post.type,
                            savedBy = currentUser,
                            postTimestamp = timestamp
                        )
                    )
                }
            }
            postViewModel.getSavedPost()
            navController.popBackStack()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            ImageSlider(
                images = post.photos,
                onClick = {
                    if(initialSavedValue != saved){
                        if(!saved){ postViewModel.removeSavedPost(authorId = post.authorId, savedBy = currentUser,timestamp= timestamp)
                        }
                        else{

                            postViewModel.savePost(
                                SavedPost(
                                    age = post.age,
                                    gender = post.gender,
                                    location = post.location,
                                    name = post.name,
                                    photos = post.photos,
                                    description = post.description,
                                    breed = post.breed,
                                    healthInformation = post.healthInformation,
                                    authorId = post.authorId,
                                    timestamp = Timestamp.now(),
                                    type = post.type,
                                    savedBy = currentUser,
                                    postTimestamp = timestamp
                                )
                            )
                        }
                        postViewModel.getSavedPost()
                    }
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

                Spacer(modifier = Modifier.weight(1f))
                if(SharedComponents.currentUser!!.uid != post.authorId){

                    Icon(
                        imageVector = if(saved) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder  ,
                        contentDescription = null,
                        tint = if(!saved) Color.Gray else Color.Red,
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    saved = !saved
                                },
                                indication = null,
                                interactionSource = interactionSource
                            )
                    )
                }
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
                Text(text = "breed:",modifier = Modifier.width(60.dp), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Text(text = post.breed,modifier =Modifier.alpha(0.6f), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(AppTheme.dimens.small))
            Row {
                Text(text = "health:",modifier = Modifier.width(60.dp), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
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

            if (appliedApplications.value.any { it.uniqueIdentifier == "${post.authorId}_${SharedComponents.currentUser!!.uid}_${SharedComponents.timeStamp}" }){
                OutlinedButton(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 8.dp),
                    enabled = false,
                    shape = RoundedCornerShape(8.dp)
                ) {

                        Text(text = "Applied")
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)

                }
            }
        else if(SharedComponents.currentUser!!.uid != post.authorId ){

                Button(
                    onClick = {
                        navController.navigate(Routes.ApplyApplicationRoute(authorId = post.authorId,post.name))
                    },
                    colors =ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 8.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Adopt")
                }
        }
            LaunchedEffect(saveOrUnSaveResponse.value) {
                when(saveOrUnSaveResponse.value){
                    is Response.Failure -> {
                        saved = false
                    }
                    Response.Loading -> {}
                    is Response.Success -> {
                        postViewModel.resetValue()
                    }
                }
            }


        }
    }
    }

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int=3,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null,
    fontSize: TextUnit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier
        .clickable(clickable) {
            isExpanded = !isExpanded
        }
        .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .alpha(0.6f)
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(style = showLessStyle) { append(showLessText) }
                    } else {
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,

            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign,
            fontSize = fontSize
        )
    }
}
