package com.loodmeet.weatherapp.ui.models

import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.models.UnitOfMeasurement

open class BottomSheetListItem(open val nameResId: Int, open val onClick: () -> Unit) {

    data class NamedBottomSheetListItem(
        override val nameResId: Int,
        override val onClick: () -> Unit
    ) : BottomSheetListItem(nameResId, onClick)

    sealed class ImagedBottomSheetListItem(
        override val nameResId: Int,
        open val imageResId: Int,
        override val onClick: () -> Unit
    ) : BottomSheetListItem(nameResId, onClick) {

        data class TemperatureItem(
            override val nameResId: Int = R.string.temperature_unit,
            override val imageResId: Int = R.drawable.ic_thermometer_1_32,
            override val onClick: () -> Unit
        ) : ImagedBottomSheetListItem(nameResId, imageResId, onClick)

        data class WindSpeedItem(
            override val nameResId: Int = R.string.wind_speed_unit,
            override val imageResId: Int = R.drawable.outline_air,
            override val onClick: () -> Unit
        ) : ImagedBottomSheetListItem(nameResId, imageResId, onClick)

        data class PrecipitationItem(
            override val nameResId: Int = R.string.precipitation_unit,
            override val imageResId: Int = R.drawable.outline_water_drop,
            override val onClick: () -> Unit
        ) : ImagedBottomSheetListItem(nameResId, imageResId, onClick)

        data class LocationItem(
            override val nameResId: Int = R.string.location,
            override val imageResId: Int = R.drawable.outline_location,
            override val onClick: () -> Unit
        ) : ImagedBottomSheetListItem(nameResId, imageResId, onClick)

        data class SelectedItem(
            override val nameResId: Int,
            override val imageResId: Int = R.drawable.baseline_check,
            override val onClick: () -> Unit
        ) : ImagedBottomSheetListItem(nameResId, imageResId, onClick)
    }
}