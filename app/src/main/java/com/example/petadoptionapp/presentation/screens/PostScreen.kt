@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.petadoptionapp.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.data.common.SharedComponents
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.presentation.viewModels.MyPostViewModel
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.ui.theme.AppTheme
import com.example.petadoptionapp.ui.theme.quickSand
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun PostScreen(
    navController: NavController,
    postScreenViewModel: PostViewModel = hiltViewModel(),
    myPostViewModel: MyPostViewModel = hiltViewModel(),
    post: Post = Post(),
    editScreen:Boolean = false,
    ) {
    val currLocation = postScreenViewModel.location.collectAsState()

    val createPostResponse = postScreenViewModel.createPostResponse.collectAsStateWithLifecycle()
    val editPostResponse = myPostViewModel.editPostResponse.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val animalTypes by rememberSaveable { mutableStateOf(listOf("Dog","Cat","Bird")) }
    val genders by rememberSaveable { mutableStateOf(listOf("Male","Female"))}
    var breedField by rememberSaveable { mutableStateOf(post.breed) }
    var cityField by rememberSaveable { mutableStateOf(post.city) }
    var stateField by rememberSaveable { mutableStateOf(post.state) }
    var countryField by rememberSaveable { mutableStateOf(post.country) }
    var descriptionField by rememberSaveable { mutableStateOf(post.description) }
    var birthDateField by rememberSaveable { mutableStateOf(post.bornOn) }
    var selectedAnimal by rememberSaveable { mutableStateOf(post.type) }
    var selectedGender by rememberSaveable { mutableStateOf(post.gender) }
    var selectedImages by rememberSaveable { mutableStateOf(post.photos)}
    var healthInformationField by rememberSaveable { mutableStateOf(post.healthInformation) }
    var isClicked by rememberSaveable{ mutableStateOf(false)}
    var nameField by rememberSaveable{ mutableStateOf(post.name)}
    var showDatePicker by rememberSaveable {
        mutableStateOf(false)
    }
    val interactionSource = remember{ MutableInteractionSource() }
    val editedValues = selectedAnimal !=post.type || selectedGender!=post.gender || nameField!=post.name || breedField != post.breed || birthDateField!=post.bornOn ||
            cityField!=post.city||stateField!=post.state|| countryField!=post.country|| descriptionField != post.description || healthInformationField != post.healthInformation || selectedImages!=post.photos

val multiImagesPicker = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(3),
    onResult = {uris: List<Uri> -> selectedImages = uris.map {it.toString() }}
)

    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = { birthDateField = it },
            onDismiss = { showDatePicker = false }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "") },
                actions = {

                        TextButton(
                            onClick = {

                                if (selectedGender==""||selectedAnimal=="" || selectedImages.isEmpty()
                                    ||breedField==""||countryField==""||descriptionField==""||birthDateField==""||healthInformationField==""||nameField ==""){
                                    Toast.makeText(context,"Fill all details.",Toast.LENGTH_SHORT).show()
                                }
                                else if(editScreen){
                                    isClicked = true
                                   myPostViewModel.editPost(
                                       Post(
                                           bornOn = birthDateField,
                                           authorId = SharedComponents.currentUser!!.uid,
                                           breed = breedField,
                                           description = descriptionField,
                                           gender = selectedGender,
                                           city = cityField,
                                           photos = selectedImages,
                                           timestamp = SharedComponents.timeStamp,
                                           type = selectedAnimal,
                                           healthInformation = healthInformationField,
                                           name = nameField,
                                           state = stateField,
                                           country = countryField
                                           ),
                                           newImages = post.photos != selectedImages,
                                            numOfImagesBefore = post.photos.size
                                   )
                                }
                                else {
                                    isClicked = true
                                    postScreenViewModel.createPost(
                                        Post(
                                            bornOn = birthDateField,
                                            authorId = SharedComponents.currentUser!!.uid,
                                            breed = breedField,
                                            description = descriptionField,
                                            gender = selectedGender,
                                            photos = selectedImages,
                                            timestamp = Timestamp.now(),
                                            type = selectedAnimal,
                                            healthInformation = healthInformationField,
                                            name = nameField,
                                            city = cityField,
                                            state = stateField,
                                            country = countryField
                                        )
                                    )

                                }

                            },

                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
                            ),
                            shape = RoundedCornerShape(8.dp)
                            ,modifier = Modifier.padding(top = 4.dp,end = AppTheme.dimens.mediumLarge),
                            enabled = !editScreen || editedValues
                        ) {
                            Text(text = if(editScreen)"save" else "post", fontWeight = FontWeight.Bold,
                                fontFamily = quickSand)
                        }

                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier =Modifier.size(30.dp)
                        )
                    }
                })
        }
    ) {padding->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = AppTheme.dimens.mediumLarge)
            .padding(top = padding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
        ) {


            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))

            Row (modifier = Modifier.fillMaxWidth()){
                animalTypes.forEach { value->
                    val selected = value == selectedAnimal
                    FilterChip(selected =  selected ,
                        onClick = { selectedAnimal=value },
                        label = { Text(text = value,fontWeight = FontWeight.SemiBold,
                            fontFamily = quickSand)},
                        leadingIcon = if (selected) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        },
                    )
                    Spacer(modifier = Modifier.width(AppTheme.dimens.mediumLarge))
                }
            }
            Row (modifier = Modifier.fillMaxWidth()){
                genders.forEach { value->
                    FilterChip(selected =  value==selectedGender ,
                        onClick = { selectedGender=value},
                        label = { Text(text = value, fontFamily = quickSand,fontWeight = FontWeight.SemiBold)},
                        leadingIcon = if (value==selectedGender) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        },
                    )
                    Spacer(modifier = Modifier.width(AppTheme.dimens.mediumLarge))
                }
            }
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))

            OutlinedTextField(
                value = nameField, onValueChange = {nameField = it}, colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                placeholder = { Text(text = "Name",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},
                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),

                )
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))

            OutlinedTextField(value = breedField, onValueChange = {breedField = it}, colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                placeholder = { Text(text = "Breed",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},

                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))

            OutlinedTextField(value = birthDateField, onValueChange = {}, colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,

                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            showDatePicker = true
                        },
                        indication = null,
                        interactionSource = interactionSource
                    ),
                shape = RoundedCornerShape(8.dp),
                placeholder = { Text(text = "Born on",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},

                enabled = false,
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),

                )
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
            OutlinedTextField(
                value =  if(countryField!="")"${cityField}, ${stateField}, $countryField" else "",
                onValueChange = {},
                modifier = Modifier

                    .clickable(
                        onClick = {

                            if (currLocation.value.lat != 0.0 && currLocation.value.long != 0.0) {

                                navController.navigate(
                                    Routes.MapScreenRoute(
                                        currLocation.value.lat.toString(),
                                        currLocation.value.long.toString()
                                    )
                                )
                            }


                        },
                        indication = null,
                        interactionSource = interactionSource
                    )
                    .fillMaxWidth(),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = MaterialTheme.colorScheme.primary  ,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground,
                ),
                placeholder = { Text(text = "Location",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},


                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),


                )
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
            OutlinedTextField(value = healthInformationField, onValueChange = {healthInformationField = it}, colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                placeholder = { Text(text = "Health Information",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},

                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),

                )
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))

            OutlinedTextField(value = descriptionField, onValueChange = {descriptionField = if (it.length >= 500) it.substring(0, 500) else it}, colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                placeholder = { Text(text = "Description",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},

                supportingText = { Text(text = "${descriptionField.length}/500",modifier =Modifier.fillMaxWidth(), textAlign = TextAlign.End)},

                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),

                )
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                ,
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                if(selectedImages.isEmpty()){
                    Column(modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.image_24), contentDescription = null,modifier =Modifier.size(30.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Upload photos",style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text(text = "Just tap here to ",style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                            Text(
                                text = "browse",
                                color = MaterialTheme.colorScheme.primaryContainer,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable(onClick = {
                                    multiImagesPicker.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

                                    )
                                },
                                    indication = null,
                                    interactionSource = interactionSource
                                ),

                                )
                            Text(text = " your gallery to",style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                        }
                        Text(text = "upload photos",style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    }
                }
                else{

                    Icon(imageVector = Icons.Default.Clear, contentDescription = null, modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.End)
                        .size(20.dp)

                        .clickable { selectedImages = emptyList() },
                        tint = Color.Gray)
                    Image(painter = painterResource(id = R.drawable.baseline_photo_library_24), contentDescription = null,modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(24.dp))


                }
            }
            if(isClicked){
                Dialog(onDismissRequest = {  }){
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 4.dp)
                }

            }
            LaunchedEffect(editPostResponse.value) {
                when(val postResponse =editPostResponse.value){
                    is Response.Failure -> {
                        isClicked = false
                        Toast.makeText(context,postResponse.e.message,Toast.LENGTH_SHORT).show()
                        myPostViewModel.resetValue()
                    }
                    Response.Loading -> {}
                    is Response.Success ->{
                        isClicked = false
                        navController.navigate(Routes.MyPostsScreen){
                            popUpTo(Routes.MyPostsScreen){inclusive = true}
                        }
                        myPostViewModel.resetValue()

                    }



                }


            }
            LaunchedEffect(createPostResponse.value) {
                when(val postResponse =createPostResponse.value){
                    is Response.Failure -> {
                        isClicked = false
                        Toast.makeText(context,postResponse.e.message,Toast.LENGTH_SHORT).show()
                        postScreenViewModel.resetValue()
                    }
                    Response.Loading -> {}
                    is Response.Success ->{
                        isClicked = false
                        navController.navigate(Routes.HomeScreen)
                        postScreenViewModel.resetValue()
                    }


                }


            }
            val cityResult = navController.currentBackStackEntry?.savedStateHandle?.get<String>("city")
            val stateResult = navController.currentBackStackEntry?.savedStateHandle?.get<String>("state")
            val countryResult = navController.currentBackStackEntry?.savedStateHandle?.get<String>("country")
            cityResult?.let {
                cityField = it
                navController.currentBackStackEntry?.savedStateHandle?.remove<String>("city")
            }
            stateResult?.let {
                stateField = it
                navController.currentBackStackEntry?.savedStateHandle?.remove<String>("state")
            }
            countryResult?.let {
                countryField = it
                navController.currentBackStackEntry?.savedStateHandle?.remove<String>("country")
            }
        }
    }

    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {

    val datePickerState = rememberDatePickerState()

    val selectedDate = datePickerState.selectedDateMillis?.let {millis->
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        formatter.format(Date(millis))

    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,


        )
    }
}





