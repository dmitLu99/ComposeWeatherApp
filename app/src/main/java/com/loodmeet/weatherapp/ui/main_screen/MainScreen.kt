package com.loodmeet.weatherapp.ui.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

data class TabItem(val title: String, val screen: @Composable () -> Unit)

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MainScreen() = with(MaterialTheme.colorScheme) {

    val tabs = listOf(
        TabItem("Today") { Screen() },
        TabItem("Tomorrow") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
    )

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
            launch { modalBottomSheetState.hide() }
            launch { mainModalBottomSheetState.show() }
        }
    }

    val savedText = LocalContext.current.getText(R.string.saved).toString()
    val closeText = LocalContext.current.getText(R.string.close).toString()

    val topNamedItemsOnClick: () -> Unit = {
        scope.apply {
            launch { mainModalBottomSheetState.hide() }
            launch { scaffoldState.snackbarHostState.showSnackbar(savedText, closeText) }
        }
    }

    val topNamedItems = listOf(
        BottomSheetListItem.ImagedBottomSheetListItem(
            R.string.location,
            R.drawable.baseline_check,
            topNamedItemsOnClick
        ),
        BottomSheetListItem.NamedBottomSheetListItem(
            R.string.location,
            topNamedItemsOnClick
        ),
        BottomSheetListItem.NamedBottomSheetListItem(
            R.string.location,
            topNamedItemsOnClick
        ),
    )

    val topItems = listOf(
        BottomSheetListItem.ImagedBottomSheetListItem(
            R.string.location,
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

    val roundedShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

    ModalBottomSheetLayout(
        sheetBackgroundColor = background,
        sheetContent = { BottomSheetContent(topItems = topNamedItems, showDragHandle = false) },
        sheetShape = roundedShape,
        sheetState = mainModalBottomSheetState
    ) {
        ModalBottomSheetLayout(
            sheetBackgroundColor = background,
            sheetContent = { BottomSheetContent(topItems = topItems, bottomItems = bottomItems) },
            sheetShape = roundedShape,
            sheetState = modalBottomSheetState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar {
                        scope.launch {
                            modalBottomSheetState.apply { if (isVisible) hide() else show() }
                        }
                    }
                },
                scaffoldState = scaffoldState
            ) { innerPadding ->
                Column(Modifier.padding(innerPadding)) { Tabs(tabs = tabs) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(moreButtonOnClick: () -> Unit = {}) = with(MaterialTheme.colorScheme) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            navigationIconContentColor = onSurface,
            actionIconContentColor = onSurface
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>) = with(MaterialTheme.colorScheme) {

    val pagerState = rememberPagerState()

    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = surface,
            contentColor = onSurface,
            edgePadding = (LocalConfiguration.current.screenWidthDp / 2).dp,
            indicator = {}
        ) {
            tabs.forEachIndexed { index, tab ->

                val shape = RoundedCornerShape(30.dp)
                val tabModifier = Modifier
                    .padding(horizontal = 3.dp, vertical = 12.dp)
                    .clip(shape)
                    .border(width = 1.dp, color = onSurface, shape = shape)
                    .background(if (pagerState.currentPage == index) primary else surface)

                TabItem(modifier = tabModifier, tab = tab, index = index, pagerState = pagerState)
            }
        }
        Pager(tabs, pagerState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabItem(modifier: Modifier = Modifier, tab: TabItem, index: Int, pagerState: PagerState) =
    with(MaterialTheme.colorScheme) {

        val scope = rememberCoroutineScope()

        Tab(
            selectedContentColor = onPrimary,
            unselectedContentColor = onSecondaryContainer,
            text = {
                Text(
                    text = tab.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (pagerState.currentPage == index) onPrimary else onSurface
                )
            },
            selected = pagerState.currentPage == index,
            onClick = {
                scope.launch {
//                            val spring = spring<Float>(stiffness = Spring.StiffnessVeryLow)
                    pagerState.animateScrollToPage(
                        page = index, pageOffset = pagerState.currentPageOffset
                    )
                }
            },
            modifier = modifier
        )
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