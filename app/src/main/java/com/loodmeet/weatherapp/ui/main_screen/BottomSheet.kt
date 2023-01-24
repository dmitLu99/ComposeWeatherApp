package com.loodmeet.weatherapp.ui.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dmitLugg.weatherapp.R

sealed class BottomSheetListItem(open val nameResId: Int, open val onClick: () -> Unit) {

    data class NamedBottomSheetListItem(
        override val nameResId: Int,
        override val onClick: () -> Unit
    ) : BottomSheetListItem(nameResId, onClick)

    data class ImagedBottomSheetListItem(
        override val nameResId: Int,
        val imageResId: Int,
        override val onClick: () -> Unit
    ) : BottomSheetListItem(nameResId, onClick)
}

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    topItems: List<BottomSheetListItem>,
    bottomItems: List<BottomSheetListItem> = listOf(),
    showDragHandle: Boolean = true,
    headingResId: Int? = null
) {
    Column(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 16.dp)
    ) {

        if (showDragHandle) DragHandle()

        if (headingResId != null) {
            Text(
                stringResource(headingResId), Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (topItems.isNotEmpty()) BottomSheetItem(items = topItems)

        if (topItems.isNotEmpty() && bottomItems.isNotEmpty()) Spacer(modifier = Modifier.height(12.dp))

        if (bottomItems.isNotEmpty()) BottomSheetItem(items = bottomItems)
    }
}

@Composable
fun DragHandle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            thickness = 4.dp,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .width(50.dp),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}


@Composable
fun BottomSheetItem(items: List<BottomSheetListItem>) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items.forEachIndexed { index, item ->
                BottomSheetListItem(item = item)
                if (index != items.size - 1) Divider()
            }
        }
    }
}

@Composable
fun BottomSheetListItem(
    item: BottomSheetListItem,
    modifier: Modifier = Modifier
) {
    if (item is BottomSheetListItem.ImagedBottomSheetListItem)
        ImagedBottomSheetListItem(item = item, modifier = modifier)
    else NamedBottomSheetListItem(
        item = item as BottomSheetListItem.NamedBottomSheetListItem, modifier = modifier
    )
}


@Composable
fun NamedBottomSheetListItem(
    item: BottomSheetListItem.NamedBottomSheetListItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(50.dp)
            .clickable(onClick = item.onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(id = item.nameResId),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ImagedBottomSheetListItem(
    item: BottomSheetListItem.ImagedBottomSheetListItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(50.dp)
            .clickable(onClick = item.onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = painterResource(id = item.imageResId)
        Icon(painter = painter, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            stringResource(id = item.nameResId),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}