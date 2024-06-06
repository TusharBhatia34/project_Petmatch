package com.example.petadoptionapp.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Collections
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.domain.model.SavedPost
import com.example.petadoptionapp.presentation.components.ImageSlider
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.ui.theme.maleSign
import com.example.petadoptionapp.ui.theme.onPrimaryDark
import com.example.petadoptionapp.ui.theme.prim
import com.example.petadoptionapp.ui.theme.primaryDark

@Composable
fun PostDetailsScreen(
    post: Post,
    navController: NavController,
    timestamp: String,
    postViewModel: PostViewModel = hiltViewModel(),
    ) {
val currentUser = Collections.currentUser!!.uid
    val localDensity = LocalDensity.current
    var nameTextHeightDp by remember { mutableStateOf(0.dp) }
    var locationTextHeightDp by remember { mutableStateOf(0.dp) }
    val savedList = SharedComponents.savedList
    var saved by remember { mutableStateOf(savedList.any { it.savedBy==Collections.currentUser!!.uid && it.timestamp == timestamp }) }
    val saveResponse = postViewModel.savePostResponse.collectAsStateWithLifecycle()
    val removeSavedPostResponse = postViewModel.removeSavedPostResponse.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
ImageSlider(
    images = post.photos,
   onClick = {
       navController.navigateUp()
   }
)

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier =Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = post.name,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
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
                tint = if(post.gender=="Male") maleSign else prim
            )

            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if(saved) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder  ,
                contentDescription = null,
                tint = if(!saved) Color.Gray else Color.Red,
                modifier = Modifier
                    .clickable(
                        onClick = {
                            if(saved){
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
                                        timestamp = timestamp,
                                        type = post.type,
                                        savedBy = currentUser
                                    )
                                )
                            }
                            saved = !saved
                        }
                    )
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
    Text(text = post.age,modifier =Modifier.alpha(0.6f))
}
        Spacer(modifier = Modifier.height(2.dp))

        Row {
    Text(text = "breed:",modifier = Modifier.width(60.dp), fontWeight = FontWeight.SemiBold)
            Text(text = post.breed,modifier =Modifier.alpha(0.6f))
        }

        Spacer(modifier = Modifier.height(2.dp))
        Row {
            Text(text = "health:",modifier = Modifier.width(60.dp), fontWeight = FontWeight.SemiBold)
            Text(text = post.healthInformation,modifier =Modifier.alpha(0.6f))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Description", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        ExpandableText(text = post.description, fontSize = 15.sp)


        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /*TODO*/ },
            colors =ButtonDefaults.buttonColors(
                containerColor = primaryDark,
                contentColor = onPrimaryDark
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp
            )) {
            Text(text = "Adopt")
        }
        LaunchedEffect(saveResponse.value) {
            when(saveResponse.value){
                is Response.Failure -> {
                    saved = false
                }
                Response.Loading -> {}
                is Response.Success -> {

                }
            }
        }
        LaunchedEffect(removeSavedPostResponse.value) {
            when(removeSavedPostResponse.value){
                is Response.Failure -> {
                    saved = true
                }
                Response.Loading -> {}
                is Response.Success -> {
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

    // Box composable containing the Text composable.
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
