package com.loodmeet.weatherapp.ui.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetContent() {
    Column {
        Text(
            "Settings", Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(Color.Red)
                .padding(start = 15.dp)
        )
        BottomSheetListItem()
        BottomSheetListItem()
        BottomSheetListItem()
        BottomSheetListItem()
        BottomSheetListItem()
    }
}


@Composable
fun BottomSheetListItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { })
            .height(55.dp)
            .background(Color.Red)
            .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Add, null)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "title", color = Color.White)
    }
}