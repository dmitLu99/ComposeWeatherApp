package com.dmitLugg.weatherapp.feature_main_screen.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(location: Location) {
    TopAppBar(
        title = {
            Text(
                text = location.country + ", " + location.city,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.LocationOn, contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}