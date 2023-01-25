package com.loodmeet.weatherapp.ui.main_screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmitLugg.weatherapp.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    val mainModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val bottomSheetListItemOnClick: () -> Unit = {
        scope.apply {
            launch {
                modalBottomSheetState.hide()
            }
            launch {
                mainModalBottomSheetState.show()
            }
        }
    }
    val savedText = stringResource(R.string.saved)
    val closeText = stringResource(R.string.close)

    val topNamedItemsOnClick: () -> Unit = {
        scope.launch {
            mainModalBottomSheetState.hide()
            scaffoldState.snackbarHostState.showSnackbar(savedText, closeText)
        }
    }

    val topNamedItems = listOf(
        BottomSheetListItem.ImagedBottomSheetListItem(
            R.string.locale,
            R.drawable.baseline_check,
            topNamedItemsOnClick
        ),
        BottomSheetListItem.NamedBottomSheetListItem(
            R.string.locale,
            topNamedItemsOnClick
        ),
        BottomSheetListItem.NamedBottomSheetListItem(
            R.string.locale,
            topNamedItemsOnClick
        ),
    )

    val topItems = listOf(
        BottomSheetListItem.ImagedBottomSheetListItem(
            R.string.locale,
            R.drawable.outline_location,
            bottomSheetListItemOnClick
        )
    )
    val bottomItems = listOf(
        BottomSheetListItem.ImagedBottomSheetListItem(
            R.string.temperature_unit,
            R.drawable.ic_thermometer_1_32,
            bottomSheetListItemOnClick
        ),
        BottomSheetListItem.ImagedBottomSheetListItem(
            R.string.wind_speed_unit,
            R.drawable.outline_air,
            bottomSheetListItemOnClick
        ),
        BottomSheetListItem.ImagedBottomSheetListItem(
            R.string.precipitation_unit,
            R.drawable.outline_water_drop,
            bottomSheetListItemOnClick
        )
    )

    ModalBottomSheetLayout(
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            BottomSheetContent(topItems = topNamedItems, showDragHandle = false)
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = mainModalBottomSheetState,

        ) {
        ModalBottomSheetLayout(
            sheetBackgroundColor = MaterialTheme.colorScheme.background,
            sheetContent = {
                BottomSheetContent(
                    topItems = topItems,
                    bottomItems = bottomItems
                )
            },
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetState = modalBottomSheetState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar {
                        scope.launch {
                            if (modalBottomSheetState.isVisible) modalBottomSheetState.hide()
                            else modalBottomSheetState.show()
                        }
                    }
                },
                scaffoldState = scaffoldState
            ) { innerPadding ->
                Column(Modifier.padding(innerPadding)) {
                    Tabs()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(moreButtonOnClick: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        title = {
            Text(
                "${stringResource(R.string.country_russia)}, ${stringResource(R.string.city_volgograd)}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = moreButtonOnClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
    )
}

data class TabItem(val title: String, val screen: @Composable () -> Unit)

val tabs = listOf(
    TabItem("Today") { Screen() },
    TabItem("Tomorrow") { Screen() },
    TabItem("Mon, 3 Feb") { Screen() },
    TabItem("Mon, 3 Feb") { Screen() },
    TabItem("Mon, 3 Feb") { Screen() },
    TabItem("Mon, 3 Feb") { Screen() },
    TabItem("Mon, 3 Feb") { Screen() },
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs() {

    val pagerState = rememberPagerState()

    val scope = rememberCoroutineScope()

    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            edgePadding = (LocalConfiguration.current.screenWidthDp / 2).dp,
            indicator = {}
        ) {
            tabs.forEachIndexed { index, tab ->
                val shape = RoundedCornerShape(30.dp)
                Tab(
                    selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    text = {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (pagerState.currentPage == index)
                                MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            val spring = spring<Float>(stiffness = Spring.StiffnessVeryLow)
                            pagerState.animateScrollToPage(
                                page = index,
                                pagerState.currentPageOffset
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 12.dp)
                        .clip(shape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = shape
                        )
                        .background(
                            if (pagerState.currentPage == index)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surface
                        )
                )
            }
        }
        Pager(tabs, pagerState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pager(tabs: List<TabItem>, pagerState: PagerState) {

    HorizontalPager(
        state = pagerState,
        count = tabs.size,
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { page ->
        tabs[page].screen()
    }
}