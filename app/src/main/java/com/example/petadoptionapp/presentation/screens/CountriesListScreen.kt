package com.example.petadoptionapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesListScreen(backButtonAction:()->Unit,onClickAction:(country:String)->Unit) {
    val countryList by remember { mutableStateOf(getCountries())}

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text("")
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                navigationIcon = {
                    IconButton(onClick = { backButtonAction() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null,tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            )

        }) {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
            .padding(top = it.calculateTopPadding())) {
            LazyColumn {
                items(countryList.size){
                    Row(modifier = Modifier.fillMaxWidth().clickable { onClickAction(countryList[it].name)}){
                        Text(text = countryList[it].name,modifier = Modifier.padding(vertical =16.dp).weight(1f), style = MaterialTheme.typography.headlineMedium, maxLines = 1)
                        Text(text = countryList[it].flag,modifier = Modifier.padding(vertical =16.dp), style = MaterialTheme.typography.headlineMedium)
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha =
                    0.7f))
                }
            }
        }
    }

}
data class Country(val name:String,val flag:String)
fun getCountries(): List<Country> {
    val isoCountryCodes = Locale.getISOCountries().toList()
    val countriesWithEmojis: MutableList<Country> = mutableListOf()
    for (countryCode in isoCountryCodes) {
        val locale = Locale("", countryCode)
        val countryName: String = locale.displayCountry
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41
        val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
        val flag =
            (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
        countriesWithEmojis.add(Country(countryName,flag))
    }
    return countriesWithEmojis
}