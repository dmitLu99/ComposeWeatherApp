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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.loodmeet.weatherapp.ui.models.BottomSheetListItem

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
fun DragHandle(modifier: Modifier = Modifier) = with(MaterialTheme) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Divider(
            thickness = 4.dp,
            modifier = Modifier
                .clip(shape = shapes.small)
                .width(50.dp),
            color = colorScheme.secondary
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
                BaseBottomSheetListItem(item = item, modifier = Modifier.height(50.dp))
                if (index != items.size - 1) Divider()
            }
        }
    }
}

@Composable
fun BaseBottomSheetListItem(
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
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 12.dp
) {

    Row(
        modifier = modifier
            .clickable(onClick = item.onClick)
            .padding(horizontal = horizontalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(id = item.nameResId),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ImagedBottomSheetListItem(
    item: BottomSheetListItem.ImagedBottomSheetListItem,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 12.dp
) = with(MaterialTheme) {

    Row(
        modifier = modifier
            .clickable(onClick = item.onClick)
            .padding(horizontal = horizontalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.imageResId),
            contentDescription = null,
            tint = colorScheme.primary,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(horizontalPadding))
        Text(
            stringResource(id = item.nameResId),
            modifier = Modifier.fillMaxWidth(),
            style = typography.bodyLarge
        )
    }
}