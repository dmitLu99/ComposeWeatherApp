package com.loodmeet.weatherapp.ui.main_screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dmitLugg.weatherapp.R
import com.google.accompanist.pager.*
import com.loodmeet.weatherapp.ui.models.BottomSheetListItem
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.loodmeet.weatherapp.core.models.Named
import com.loodmeet.weatherapp.core.models.MeasurementUnit
import com.loodmeet.weatherapp.ui.models.MainScreenTabItem
import com.loodmeet.weatherapp.ui.veiw_models.MainScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewModelScope
import com.loodmeet.weatherapp.core.models.Language
import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.Settings
import com.loodmeet.weatherapp.core.models.Theme
import com.loodmeet.weatherapp.ui.models.Weather
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(settings: Settings, viewModel: MainScreenViewModel = viewModel()): Unit =
    with(MaterialTheme.colorScheme) {
        setLocale(settings.language, LocalConfiguration.current, LocalContext.current)
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    snackbarHostState,
                    modifier = Modifier.background(background)
                )
            },
            backgroundColor = background
        ) { padding ->

            if (viewModel.getIsLoading().value) {
                Loading()
            } else if (viewModel.getIsError().value) {
                Error(
                    viewModel = viewModel,
                    modifier = Modifier.padding(padding),
                    snackbarHostState = snackbarHostState
                )
            } else {
                if (viewModel.showNotificationFromOpenMeteo().value) {
                    val context = LocalContext.current

                    viewModel.viewModelScope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = context.getString(R.string.value_from_open_meteo),
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                        when (result) {
                            SnackbarResult.Dismissed -> {}
                            else -> {}
                        }
                    }
                    viewModel.showNotificationFromOpenMeteo().value = false
                }

                MainView(
                    modifier = Modifier.padding(padding),
                    weatherList = viewModel.getWeatherData(),
                    viewModel = viewModel,
                    settings = settings
                )
            }
        }
    }

fun setLocale(language: Language, localConfig: Configuration, context: Context) {
    val locale = Locale(language.getTag())
    Locale.setDefault(locale)
    localConfig.setLocale(locale)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        localConfig.setLocale(locale)
    else
        localConfig.locale = locale
    var resources = context.resources
    resources.updateConfiguration(localConfig, resources.displayMetrics)
}

@Composable
fun Error(
    viewModel: MainScreenViewModel = viewModel(),
    modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState
): Unit = with(MaterialTheme.colorScheme) {
    val scope = rememberCoroutineScope()


    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = viewModel::fetchWeather
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = surfaceTint,
                modifier = Modifier.size(56.dp)
            )
        }
    }

    val context = LocalContext.current
    scope.launch {
        val result = snackbarHostState.showSnackbar(
            message = context.getString(R.string.error_load),
            withDismissAction = true,
            duration = SnackbarDuration.Long
        )
        when (result) {
            SnackbarResult.Dismissed -> {}
            else -> {}
        }
    }

}

@Composable
fun Loading() = with(MaterialTheme.colorScheme) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator(Modifier.fillMaxSize(0.1f))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = viewModel(),
    weatherList: List<Weather>,
    settings: Settings,
) =
    with(MaterialTheme.colorScheme) {

        val tabs = listOf(
            MainScreenTabItem(weatherList[0].date) { WeatherScreen(weatherList[0]) },
            MainScreenTabItem(weatherList[1].date) { WeatherScreen(weatherList[1]) },
            MainScreenTabItem(weatherList[2].date) { WeatherScreen(weatherList[2]) },
            MainScreenTabItem(weatherList[3].date) { WeatherScreen(weatherList[3]) },
            MainScreenTabItem(weatherList[4].date) { WeatherScreen(weatherList[4]) },
            MainScreenTabItem(weatherList[5].date) { WeatherScreen(weatherList[5]) },
            MainScreenTabItem(weatherList[6].date) { WeatherScreen(weatherList[6]) },
        )

        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        val modalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
        )
        val mainModalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
        )

        var bottomSheetItems by remember {
            mutableStateOf(listOf<BottomSheetListItem>())
        }

        val bottomSheetListItemOnClick: (items: List<BottomSheetListItem>) -> Unit = { items ->
            bottomSheetItems = items
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

        var selectedLocation = settings.location
        var selectedTheme = settings.theme
        var selectedLanguage = settings.language

        val selectedUnitsSet = settings.measurementUnits
        var selectedTemperature = selectedUnitsSet.temperatureUnit
        var selectedWindSpeed = selectedUnitsSet.windSpeedUnit
        var selectedPrecipitation = selectedUnitsSet.precipitationUnit

        val config = LocalConfiguration.current
        val context = LocalContext.current
        val languageItems = mapToBottomSheetListItem(
            items = Language.getList(),
            onClick = topNamedItemsOnClick,
            isClicked = { item -> item == selectedLanguage },
            onSelect = { item ->
                selectedLanguage = item
                viewModel.changeSettings(language = item)
                setLocale(language = item, context =context, localConfig = config)
            }
        )

        val themeItems = mapToBottomSheetListItem(
            items = Theme.getList(),
            onClick = topNamedItemsOnClick,
            isClicked = { item -> item == selectedTheme },
            onSelect = { item ->
                selectedTheme = item
                viewModel.changeSettings(theme = item)
            }
        )

        val locationItems = mapToBottomSheetListItem(
            items = Location.getList(),
            onClick = topNamedItemsOnClick,
            isClicked = { item -> item == selectedLocation },
            onSelect = { item ->
                selectedLocation = item
                viewModel.changeSettings(location = item)
            }
        )

        val temperatureItems = mapToBottomSheetListItem(
            items = listOf(
                MeasurementUnit.TemperatureUnit.Celsius,
                MeasurementUnit.TemperatureUnit.Fahrenheit
            ),
            onClick = topNamedItemsOnClick,
            isClicked = { item -> item == selectedTemperature },
            onSelect = { item ->
                selectedTemperature = item
                viewModel.changeSettings(measurementUnitsSet =
                selectedUnitsSet.apply {
                    temperatureUnit = item
                })
            }
        )

        val windSpeedItems = mapToBottomSheetListItem(
            items = listOf(
                MeasurementUnit.WindUnitSpeedUnit.KilometresPerHour,
                MeasurementUnit.WindUnitSpeedUnit.MetresPerSecond,
                MeasurementUnit.WindUnitSpeedUnit.MilesPerHour,
                MeasurementUnit.WindUnitSpeedUnit.Knots
            ),
            onClick = topNamedItemsOnClick,
            isClicked = { item -> item == selectedWindSpeed },
            onSelect = { item ->
                selectedWindSpeed = item
                viewModel.changeSettings(measurementUnitsSet =
                selectedUnitsSet.apply {
                    windSpeedUnit = item
                })
            }
        )

        val precipitationItems = mapToBottomSheetListItem(
            items = listOf(
                MeasurementUnit.PrecipitationUnit.Millimeter,
                MeasurementUnit.PrecipitationUnit.Inch
            ),
            onClick = topNamedItemsOnClick,
            isClicked = { item -> item == selectedPrecipitation },
            onSelect = { item ->
                selectedPrecipitation = item
                viewModel.changeSettings(measurementUnitsSet =
                selectedUnitsSet.apply {
                    precipitationUnit = item
                })
            }
        )

        val topItems = listOf(
            BottomSheetListItem.ImagedBottomSheetListItem.LanguageItem {
                bottomSheetListItemOnClick(languageItems)
            },
            BottomSheetListItem.ImagedBottomSheetListItem.LocationItem {
                bottomSheetListItemOnClick(locationItems)
            },
            BottomSheetListItem.ImagedBottomSheetListItem.ThemeItem {
                bottomSheetListItemOnClick(themeItems)
            }
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
            sheetContent = {
                BottomSheetContent(
                    topItems = bottomSheetItems,
                    showDragHandle = true
                )
            },
            sheetShape = roundedShape,
            sheetState = mainModalBottomSheetState,
            modifier = modifier
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
                        TopAppBar(location = selectedLocation) {
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
fun TopAppBar(location: Location, moreButtonOnClick: () -> Unit = {}) =
    with(MaterialTheme.colorScheme) {

        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                navigationIconContentColor = onSurface,
                actionIconContentColor = onSurface
            ),
            title = {
                Text(
                    "${stringResource(location.cityResId)}, ${stringResource(location.countryResId)}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
//        navigationIcon = {
//            IconButton(onClick = { /* doSomething() */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Menu,
//                    contentDescription = null
//                )
//            }
//        },
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
                    text = tab.title.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (pagerState.currentPage == index) onPrimary else onSurface
                )
            },
            selected = pagerState.currentPage == index,
            onClick = {
                scope.launch {
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