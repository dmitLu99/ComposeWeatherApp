package com.loodmeet.weatherapp.ui.main_screen

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.DismissState
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.loodmeet.weatherapp.app.ui.theme.ComposeWeatherAppTheme
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.feature_main_screen.ui.models.Location
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    if(state.currentValue != state.targetValue) Log.d(Config.LOG.UI_LOG_TAG, "1")


    ComposeWeatherAppTheme {
        ModalBottomSheetLayout(
            sheetContentColor = Color.White,
            sheetBackgroundColor = MaterialTheme.colorScheme.background,
            sheetContent = {
                BottomSheetContent(state = state)
            },
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetState = state,

        ) {

            Scaffold(
                topBar = {
                    TopAppBar {
                        scope.launch {
                            if (state.isVisible) {
                                state.hide()
                            } else {
                                state.show()
                            }
                        }
                    }
                },
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
        title = {
            Text(
                "Russia, Volgograd",
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

class TabItem(
    val title: String, val screen: @Composable () -> Unit
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun Tabs() {

    val tabs = listOf(
        TabItem("Today") { Screen() },
        TabItem("Tomorrow") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
        TabItem("Mon, 3 Feb") { Screen() },
    )
    val pagerState = rememberPagerState()

    val scope = rememberCoroutineScope()

    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            edgePadding = (LocalConfiguration.current.screenWidthDp / 3).dp,
//            indicator = {},
            indicator = { }
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
                                MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
//                            pagerState.animateScrollToPage(index)
                            spring<Float>(stiffness = Spring.StiffnessLow)
                            pagerState.animateScrollToPage(
                                page = index,
                                pagerState.currentPageOffset
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 6.dp)
                        .clip(shape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = shape
                        )
                        .background(
                            if (pagerState.currentPage == index)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surface
                        ),

                    )
            }
        }
        TabsContent(tabs, pagerState)
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {

    HorizontalPager(
        state = pagerState,
        count = tabs.size,
        contentPadding = when (pagerState.currentPage) {
            0 -> PaddingValues(end = 20.dp)
            pagerState.pageCount - 1 -> PaddingValues(start = 30.dp)
            else -> PaddingValues(horizontal = 20.dp)
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { page ->
        tabs[page].screen()
    }
    Log.d(Config.LOG.UI_LOG_TAG, "${pagerState.pageCount} ${pagerState.currentPage}")
}



