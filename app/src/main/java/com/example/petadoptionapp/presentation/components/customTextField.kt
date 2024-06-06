package com.example.petadoptionapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    title:String,
    value:String,
    onValueChange:(String)->Unit,
    placeholder:String="",
    singleLine:Boolean = true
) {
Column(modifier = Modifier.fillMaxWidth()) {

Text(text = title,Modifier.padding(vertical = 8.dp), fontSize = 15.sp,color = Color.Black)
    OutlinedTextField(value = value, onValueChange = onValueChange, colors = OutlinedTextFieldDefaults.colors(
       focusedBorderColor = Color.Blue,
        unfocusedBorderColor = Color.Black
    ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(text = placeholder)},
        singleLine = singleLine
    )

}
}


