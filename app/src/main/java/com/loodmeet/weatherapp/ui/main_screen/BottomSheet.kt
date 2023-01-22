package com.loodmeet.weatherapp.ui.main_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(modifier: Modifier = Modifier, state: ModalBottomSheetState) {
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                thickness = 5.dp,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .width(50.dp),
//                color =  MaterialTheme.colorScheme.onSecondaryContainer
                color = if (state.targetValue != state.currentValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Text(
            "Settings", Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 15.dp),
            color = MaterialTheme.colorScheme.onBackground
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
            .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Add, null, tint = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "title", color = MaterialTheme.colorScheme.onBackground)
    }
}