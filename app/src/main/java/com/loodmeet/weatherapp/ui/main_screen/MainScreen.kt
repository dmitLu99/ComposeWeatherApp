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
import com.loodmeet.weatherapp.ui.models.BottomSheetListItem
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.loodmeet.weatherapp.core.models.Named
import com.loodmeet.weatherapp.core.models.UnitOfMeasurement
import com.loodmeet.weatherapp.ui.models.MainScreenTabItem
import com.loodmeet.weatherapp.ui.veiw_models.MainScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.ui.models.Weather

@Preview
@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {

    if (viewModel.weatherData.value == null) {
        Init()
        viewModel.fetchWeather()
    } else {
        MainView(weather = viewModel.weatherData.value!!)
    }
}

@Composable
fun Init() {

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator(Modifier.fillMaxSize(0.1f))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(viewModel: MainScreenViewModel = viewModel(), weather: Weather) = with(MaterialTheme.colorScheme) {

    val tabs = listOf(
        MainScreenTabItem("Today") { WeatherScreen(weather) },
        MainScreenTabItem("Tomorrow") { WeatherScreen(weather) },
        MainScreenTabItem("Mon, 3 Feb") { WeatherScreen(weather) },
        MainScreenTabItem("Mon, 3 Feb") { WeatherScreen(weather) },
        MainScreenTabItem("Mon, 3 Feb") { WeatherScreen(weather) },
        MainScreenTabItem("Mon, 3 Feb") { WeatherScreen(weather) },
        MainScreenTabItem("Mon, 3 Feb") { WeatherScreen(weather) },
    )

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    val mainModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    var unitItems by remember {
        mutableStateOf(listOf<BottomSheetListItem>())
    }

    val bottomSheetListItemOnClick: (items: List<BottomSheetListItem>) -> Unit = { items ->
        unitItems = items
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

    var selectedTemperature: UnitOfMeasurement.TemperatureUnit by remember {
        mutableStateOf(UnitOfMeasurement.TemperatureUnit.Celsius)
    }

    var selectedWindSpeed: UnitOfMeasurement.WindSpeedUnit by remember {
        mutableStateOf(UnitOfMeasurement.WindSpeedUnit.MetresPerSecond)
    }

    var selectedPrecipitation: UnitOfMeasurement.PrecipitationUnit by remember {
        mutableStateOf(UnitOfMeasurement.PrecipitationUnit.Millimeter)
    }

    val temperatureItems = mapToBottomSheetListItem(
        items = listOf(
            UnitOfMeasurement.TemperatureUnit.Celsius,
            UnitOfMeasurement.TemperatureUnit.Fahrenheit
        ),
        onClick = topNamedItemsOnClick,
        isClicked = { item -> item == selectedTemperature },
        onSelect = { item -> selectedTemperature = item }
    )

    val windSpeedItems = mapToBottomSheetListItem(
        items = listOf(
            UnitOfMeasurement.WindSpeedUnit.KilometresPerHour,
            UnitOfMeasurement.WindSpeedUnit.MetresPerSecond,
            UnitOfMeasurement.WindSpeedUnit.MilesPerHour,
            UnitOfMeasurement.WindSpeedUnit.Knots
        ),
        onClick = topNamedItemsOnClick,
        isClicked = { item -> item == selectedWindSpeed },
        onSelect = { item ->
            selectedWindSpeed = item
            viewModel.changeWindSpeedUnit(item)
        }
    )

    val precipitationItems = mapToBottomSheetListItem(
        items = listOf(
            UnitOfMeasurement.PrecipitationUnit.Millimeter,
            UnitOfMeasurement.PrecipitationUnit.Inch
        ),
        onClick = topNamedItemsOnClick,
        isClicked = { item -> item == selectedPrecipitation },
        onSelect = { item -> selectedPrecipitation = item }
    )

    val topItems = listOf(
        BottomSheetListItem.ImagedBottomSheetListItem.LocationItem {}
    )

    val bottomItems = listOf(
        BottomSheetListItem.ImagedBottomSheetListItem.TemperatureItem {
            bottomSheetListItemOnClick(temperatureItems)
        },
        BottomSheetListItem.ImagedBottomSheetListItem.WindSpeedItem {
            bottomSheetListItemOnClick(windSpeedItems)
        },
        BottomSheetListItem.ImagedBottomSheetListItem.PrecipitationItem {
            bottomSheetListItemOnClick(precipitationItems)
        }
    )

    val roundedShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

    ModalBottomSheetLayout(
        sheetBackgroundColor = background,
        sheetContent = { BottomSheetContent(topItems = unitItems, showDragHandle = true) },
        sheetShape = roundedShape,
        sheetState = mainModalBottomSheetState
    ) {
        ModalBottomSheetLayout(
            sheetBackgroundColor = background,
            sheetContent = {
                BottomSheetContent(
                    topItems = topItems,
                    bottomItems = bottomItems
                )
            },
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
//    }
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
fun Tabs(tabs: List<MainScreenTabItem>) = with(MaterialTheme.colorScheme) {

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
fun TabItem(
    modifier: Modifier = Modifier,
    tab: MainScreenTabItem,
    index: Int,
    pagerState: PagerState
) =
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
fun Pager(tabs: List<MainScreenTabItem>, pagerState: PagerState) {

    HorizontalPager(
        state = pagerState,
        count = tabs.size,
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { page ->
        tabs[page].screen()
    }
}

private fun <T : Named> mapToBottomSheetListItem(
    items: List<T>,
    onClick: () -> Unit,
    isClicked: (T) -> Boolean,
    onSelect: (T) -> Unit
): List<BottomSheetListItem> {

    return items.map { item ->
        if (isClicked(item)) {
            BottomSheetListItem.ImagedBottomSheetListItem.SelectedItem(
                nameResId = item.nameResId,
            ) {
                onClick()
            }
        } else {
            BottomSheetListItem.NamedBottomSheetListItem(
                nameResId = item.nameResId
            ) {
                onSelect(item)
                onClick()
            }
        }
    }
}