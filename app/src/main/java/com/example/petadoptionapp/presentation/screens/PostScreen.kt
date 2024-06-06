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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Collections
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.domain.model.Post
import com.example.petadoptionapp.presentation.components.CustomTextField
import com.example.petadoptionapp.presentation.viewModels.PostViewModel
import com.example.petadoptionapp.ui.theme.onPrimaryDark
import com.example.petadoptionapp.ui.theme.primaryDark
import com.google.firebase.Timestamp

@Composable
fun PostScreen(
    navController: NavController,
    postScreenViewModel: PostViewModel = hiltViewModel(),
    ) {
    val response = postScreenViewModel.createPostResponse.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val animalTypes =listOf("Dog","Cat","Bird")
    val gender = listOf("Male","Female")
    var breedField by remember { mutableStateOf("") }
    var locationField by remember { mutableStateOf("") }
    var descriptionField by remember { mutableStateOf("") }
    var ageField by remember { mutableStateOf("") }
    var selectedAnimal by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList())}
    var healthInformationField by remember { mutableStateOf("") }
    var isClicked by remember{ mutableStateOf(false)}
    var name by remember{ mutableStateOf("")}

val multiImagesPicker = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(3),
    onResult = {uris: List<Uri> -> selectedImages=uris }
)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(horizontal = 12.dp)
        .verticalScroll(rememberScrollState())
        ) {
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = onPrimaryDark,
                modifier = Modifier.size(30.dp)
            )
            TextButton(
                onClick = {

                    if (selectedGender==""&&selectedAnimal=="" && selectedImages.isEmpty()
                  &&breedField==""&&locationField==""&&descriptionField==""&&ageField==""&&healthInformationField==""&&name ==""){
            Toast.makeText(context,"Fill all remaining details!",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        postScreenViewModel.createPost(
                            Post(
                                age = ageField,
                                authorId = Collections.currentUser!!.uid,
                                breed = breedField,
                                description = descriptionField,
                                gender = selectedGender,
                                location = locationField,
                                photos = selectedImages.map {it.toString() },
                                timestamp = Timestamp.now(),
                                type = selectedAnimal,
                                healthInformation = healthInformationField,
                                name = name
                            )
                        )

                    }

                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = primaryDark,
                    contentColor = onPrimaryDark
                ),

            ) {
                if(!isClicked) {
                    Text(text = "post", color = onPrimaryDark)
                }
                else{
                    CircularProgressIndicator(color = onPrimaryDark,modifier = Modifier.size(ButtonDefaults.IconSize), strokeWidth = 2.dp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Type",Modifier.padding(bottom = 1.dp), fontSize = 15.sp,color = Color.Black)
        Row (modifier = Modifier.fillMaxWidth()){
            animalTypes.forEach { value->
                FilterChip(selected =  value==selectedAnimal ,
                    onClick = { selectedAnimal=value},
                    label = { Text(text = value)},
                    leadingIcon = if (value==selectedAnimal) {
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
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Gender",Modifier, fontSize = 15.sp,color = Color.Black)
        Row (modifier = Modifier.fillMaxWidth()){
            gender.forEach { value->
                FilterChip(selected =  value==selectedGender ,
                    onClick = { selectedGender=value},
                    label = { Text(text = value)},
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
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

    CustomTextField(
    title = "Name",
    value = name,
    onValueChange = { name = it },
    placeholder = "Enter Name"
)
Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
    title = "Breed",
    value = breedField,
    onValueChange = { breedField = it },
    placeholder = "Enter Breed"
)
Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            title = "Age",
            value = ageField,
            onValueChange = { ageField = it },
            placeholder = "eg.1 years, 3 months"
        )
        Spacer(modifier = Modifier.height(16.dp))
CustomTextField(
    title = "Location",
    value = locationField,
    onValueChange = { locationField = it },
    placeholder = "City,Country"
)
        Spacer(modifier =  Modifier.height(16.dp))
        CustomTextField(
            title = "Description",
            value = descriptionField,
            onValueChange = { descriptionField = it },
            singleLine = false,
            placeholder = "eg. behavior, habit, routine"
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            title = "Health Information",
            value =healthInformationField,
            onValueChange = { healthInformationField = it },
            singleLine = false,
            placeholder = "eg. vaccination, spayed/neutered status"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Add Photos",Modifier.padding(top = 8.dp, bottom = 8.dp), fontSize = 15.sp,color = Color.Black)
OutlinedCard(
    modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
        ,
    border = BorderStroke(width = 1.dp, color = Color.Black)
) {
Column(modifier = Modifier
    .padding(vertical = 16.dp)
    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
Image(painter = painterResource(id = R.drawable.image_24), contentDescription = null,modifier =Modifier.size(30.dp))
Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Upload photos")
    Spacer(modifier = Modifier.height(4.dp))
    Row {
        Text(text = "Just tap here to ")
        Text(
            text = "browse",
            color = Color.Blue,
            modifier = Modifier.clickable(onClick = {
                multiImagesPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

                )
            })
        )
        Text(text = " your gallery to")
    }
    Text(text = "upload photos")
}
}
        LaunchedEffect(response.value) {
            when(val postResponse =response.value){
                is Response.Failure -> {
                    Toast.makeText(context,postResponse.e.message,Toast.LENGTH_SHORT).show()
                }
                Response.Loading -> isClicked = true
                is Response.Success ->
                    if(postResponse.data) {
                    isClicked = false
                    postScreenViewModel.resetValue()
                        navController.navigate(Routes.HomeScreen)
                }
            }


        }
  }
    }




