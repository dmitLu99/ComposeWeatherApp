package com.loodmeet.weatherapp.ui.main_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.app.ui.theme.ComposeWeatherAppTheme
import com.loodmeet.weatherapp.core.ui.composable.TextRow
import com.loodmeet.weatherapp.core.ui.composable.VerticalDivider
import com.loodmeet.weatherapp.core.ui.models.Units
import com.loodmeet.weatherapp.core.ui.models.UnitsOfMeasurementResIds
import com.loodmeet.weatherapp.feature_daily_weather.ui.models.DailyWeather
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent()
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs() {

//    var state by remember { mutableStateOf(0) }

    val tabs = listOf(
        TabItem("Today") { Screen(1) },
        TabItem("Tomorrow") { Screen(2) },
        TabItem("Mon, 3 Feb") { Screen(3) },
        TabItem("Mon, 3 Feb") { Screen(4) },
        TabItem("Mon, 3 Feb") { Screen(5) },
        TabItem("Mon, 3 Feb") { Screen(6) },
        TabItem("Mon, 3 Feb") { Screen(7) },
    )
    val pagerState = rememberPagerState()

    val scope = rememberCoroutineScope()

    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
//            contentColor = Color.Black,
            edgePadding = (LocalConfiguration.current.screenWidthDp / 3).dp,
            indicator = {},
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
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
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            if (pagerState.currentPage == index)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.secondaryContainer
                        )
                )
            }
        }
        TabsContent(tabs, pagerState)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, pageCount = tabs.size) { page ->
        tabs[page].screen()
    }
}

@Composable
fun Settings() {

}

