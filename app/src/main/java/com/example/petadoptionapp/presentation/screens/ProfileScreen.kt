package com.example.petadoptionapp.presentation.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.petadoptionapp.R
import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.domain.model.Location
import com.example.petadoptionapp.ui.theme.AppTheme
import com.example.petadoptionapp.ui.theme.quickSand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    name:String,
    about:String,
    profilePicture:String,
    location:String,
    screenTitle:String,
    currentLocation: Location,
    buttonText:String,
    buttonAction :(name:String, location:String,about:String,profilePicture:String,locationInDouble:Location,sameImage:Boolean)->Unit,
    responseAction :()->Unit,
    enabled:Boolean= true,
    navController: NavController,
    response: State<Response<Boolean>>
) {

        var usernameField by rememberSaveable{ mutableStateOf(name) }
        var aboutField by rememberSaveable{ mutableStateOf(about) }
        var profilePictureField by rememberSaveable{ mutableStateOf(profilePicture)}
        var locationField by rememberSaveable{ mutableStateOf(location) }
        var locationInDouble by remember {mutableStateOf(currentLocation) }
        var isClicked by remember { mutableStateOf(false) }
        val initialUsernameField by rememberSaveable{ mutableStateOf(name) }
        val initialAboutField by rememberSaveable{ mutableStateOf(about) }
        val initialProfilePictureField by rememberSaveable{ mutableStateOf(profilePicture)}
        val initialLocationField by rememberSaveable{ mutableStateOf(location) }
        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> if(uri!=null)profilePictureField = uri.toString()  }
        )
    val interactionSource = remember{ MutableInteractionSource() }

        val context = LocalContext.current
Scaffold (
    bottomBar = {

        BottomAppBar(actions = {
            Button(onClick = {

                if(enabled){
                    if(usernameField!=""&&aboutField !=""&&locationField!=""){
                        isClicked = true
val sameImage = initialProfilePictureField == profilePictureField
                        buttonAction(usernameField, locationField, aboutField,profilePictureField ,locationInDouble,sameImage)
                    }
                    else {
                        Toast.makeText(context,"Please fill all the details.",Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    navController.navigate(Routes.EditProfileScreen)
                }

            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.mediumLarge)
                ,
                shape = RoundedCornerShape(8.dp),
                enabled = (!enabled ||initialLocationField!=locationField || initialUsernameField!=usernameField || initialProfilePictureField != profilePictureField || initialAboutField!= aboutField)
            ) {

                Text(
                    text = buttonText,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = quickSand
                )
            }
        }, containerColor = MaterialTheme.colorScheme.surface )
    },
    topBar = {
        TopAppBar(title = { Text(
            text = screenTitle,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(vertical = 18.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )  },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null )
                }
            })
    }
){ padding ->
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(horizontal = AppTheme.dimens.mediumLarge)
            .padding(bottom = padding.calculateBottomPadding(), top = padding.calculateTopPadding())
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {


        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.BottomCenter
        ){
            if(profilePictureField==""){
                Image(painter = painterResource(id = R.drawable.defaultpp), contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(180.dp))
            }
            else{
                SubcomposeAsyncImage(model =profilePictureField, contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(180.dp),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(35.dp),color = MaterialTheme.colorScheme.inversePrimary)

                        }
                    }
                )
            }

            if(enabled){
                Image(
                    painter = painterResource(id = R.drawable.editphoto),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 25.dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .align(Alignment.BottomEnd)
                        .clickable(onClick = {
                            imagePicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        })
                )
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.large))
        OutlinedTextField(
            value = usernameField,
            onValueChange = {  usernameField =  if (it.length >= 30) it.substring(0, 30) else it },
            modifier = Modifier
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledTextColor = MaterialTheme.colorScheme.onBackground
            ),
            placeholder = { Text(text = "Username",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},
            singleLine = true,
            enabled = enabled,
            supportingText = { if(enabled)Text(text = "${usernameField.length}/30",modifier =Modifier.fillMaxWidth(), textAlign = TextAlign.End)},
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),

            )
        Spacer(modifier = Modifier.height(AppTheme.dimens.small))
        OutlinedTextField(
            value = locationField ,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        if (enabled && currentLocation.lat != 0.0 && currentLocation.long != 0.0) {
                            navController.navigate(
                                Routes.MapScreenRoute(
                                    currentLocation.lat.toString(),
                                    currentLocation.long.toString()
                                )
                            )
                        }
                    },
                    indication = null,
                    interactionSource = interactionSource
                ),
                    enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledTextColor = MaterialTheme.colorScheme.onBackground
            ),

            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(text = "Choose a location",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},
            textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),

            )
        Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
        OutlinedTextField(
            value = aboutField,
            onValueChange = {  aboutField = if (it.length >= 500) it.substring(0, 500) else it},
            modifier = Modifier
                .fillMaxWidth()
            ,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledTextColor = MaterialTheme.colorScheme.onBackground
            ),
            placeholder = { Text(text = "About",color = Color.Gray, fontFamily = quickSand, fontWeight = FontWeight.SemiBold)},
            enabled = enabled,

            supportingText = { if(enabled)Text(text =  "${aboutField.length}/500",modifier =Modifier.fillMaxWidth(), textAlign = TextAlign.End)},
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),

            )
        Spacer(modifier = Modifier.weight(1f))


    }
}

    if(enabled){
        LaunchedEffect(response.value) {
            when(val res = response.value){
                is Response.Failure -> {

                    Toast.makeText(context, res.e.message, Toast.LENGTH_SHORT).show()
                    isClicked = false
                }
                Response.Loading -> {
                }
                is Response.Success -> {
                    isClicked = false
                    responseAction()

                }
            }
        }
    }
    if(isClicked){
        Dialog(onDismissRequest = {  }){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 4.dp)
        }

    }

        val resultLocation = navController.currentBackStackEntry?.savedStateHandle?.get<String>("location")
        resultLocation?.let {
            locationField = it
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("location")
        }
    val locationDouble = navController.currentBackStackEntry?.savedStateHandle?.get<Pair<Double,Double>>("locationInDouble")
    locationDouble?.let {(lat,long)->
        locationInDouble = Location(lat =lat, long = long)
        navController.currentBackStackEntry?.savedStateHandle?.remove<Pair<Double,Double>>("locationInDouble")
    }

    }




















