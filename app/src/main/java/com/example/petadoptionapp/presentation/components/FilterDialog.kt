package com.example.petadoptionapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.petadoptionapp.data.common.Routes
import com.example.petadoptionapp.domain.model.Location
import com.example.petadoptionapp.ui.theme.AppTheme
import com.example.petadoptionapp.ui.theme.quickSand

@Composable
fun FilterDialog(
    applyAction:(type: String, gender: String, breed: String, city: String, state: String, country: String)-> Unit,
    navController: NavController,
    currLocation:Location,
    onDismiss:()-> Unit
) {
    val animalTypes by rememberSaveable { mutableStateOf(listOf("Dog","Cat","Bird")) }
    var selectedAnimal by rememberSaveable { mutableStateOf("") }
    val gender by rememberSaveable { mutableStateOf(listOf("Male","Female"))}
    var selectedGender by rememberSaveable { mutableStateOf("") }
    val interactionSource = remember{ MutableInteractionSource() }
    var breedField by rememberSaveable { mutableStateOf("") }
    var cityField by rememberSaveable { mutableStateOf("") }
    var stateField by rememberSaveable { mutableStateOf("") }
    var countryField by rememberSaveable { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)) {

                IconButton(onClick = { onDismiss() },modifier = Modifier
                    .wrapContentSize()
                    .size(24.dp)
                    .padding(0.dp)
                    .align(Alignment.End)
                    ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null,modifier = Modifier

                        .padding(0.dp))
                }


            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
            Text(text = "Animal Type",
                fontWeight = FontWeight.Bold,
                fontFamily = quickSand,
                color = MaterialTheme.colorScheme.onBackground,
                )
            Spacer(modifier = Modifier.height(AppTheme.dimens.smallMedium))
            Row (modifier = Modifier.fillMaxWidth()){
                animalTypes.forEach { value->
                    val selected = value == selectedAnimal
                    FilterChip(shape = RoundedCornerShape(8.dp),selected =  selected ,
                        onClick = { selectedAnimal = if (selectedAnimal==value) "" else value },
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
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
            Text(text = "Gender",
                fontWeight = FontWeight.Bold,
                fontFamily = quickSand,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.smallMedium))
            Row (modifier = Modifier.fillMaxWidth()){
                gender.forEach { value->
                    val selected = value == selectedGender
                    FilterChip(shape = RoundedCornerShape(8.dp), selected =  selected ,
                        onClick = { selectedGender= if(selectedGender==value) "" else value },
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
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
            Text(text = "Breed",
                fontWeight = FontWeight.Bold,
                fontFamily = quickSand,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.medium))
            OutlinedTextField(
                value = breedField , onValueChange = {breedField = it}, colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =  MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),
                trailingIcon = {
                    if(breedField!=""){

                        IconButton(onClick = { breedField="" }) {
                            Icon(imageVector = Icons.Rounded.Clear, contentDescription = null)
                        }
                    }
                }
                )
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
            Text(text = "Location",
                fontWeight = FontWeight.Bold,
                fontFamily = quickSand,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.medium))
            OutlinedTextField(
                value =  if(countryField!="")"${cityField}, ${stateField}, $countryField" else "" ,
                onValueChange = {},
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = MaterialTheme.colorScheme.primary  ,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier
                    .clickable(
                        onClick = {

                            if (currLocation.lat != 0.0 && currLocation.long != 0.0) {

                                navController.navigate(
                                    Routes.MapScreenRoute(
                                        currLocation.lat.toString(),
                                        currLocation.long.toString()
                                    )
                                )
                            }
                        },
                        indication = null,
                        interactionSource = interactionSource
                    ).fillMaxWidth(),
                enabled = false,
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = quickSand),
                trailingIcon = {
                    if(countryField!=""){
                        IconButton(onClick = { countryField="";stateField="";cityField="" }) {
                            Icon(imageVector = Icons.Rounded.Clear, contentDescription = null)
                        }
                    }
                }

                )
            Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))
            Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
Button(onClick = {
    countryField="";stateField="";cityField="";breedField="";selectedGender="";selectedAnimal=""
},
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    shape = RoundedCornerShape(8.dp)) {
   Text(text = "Reset", fontWeight = FontWeight.Bold, fontFamily = quickSand)
}
                Button(onClick = {
                    onDismiss()
                    navController.navigate(Routes.FilterPostsRoute("Result"))
                    applyAction(
                    selectedAnimal,selectedGender,breedField,cityField,stateField,countryField
                ) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(8.dp)) {
   Text(text = "Apply", fontWeight = FontWeight.Bold, fontFamily = quickSand)
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

