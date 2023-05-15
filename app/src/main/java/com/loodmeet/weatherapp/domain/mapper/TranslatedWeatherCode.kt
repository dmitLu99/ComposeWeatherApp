package com.loodmeet.weatherapp.domain.mapper

import com.dmitLugg.weatherapp.R

enum class TranslatedWeatherCode {

    CLEAR_SKY {
        override val stringResId = R.string.clear_sky
        override val dayImageResId: Int = R.drawable.ic_sun_32
        override val nightImageResId: Int = R.drawable.ic_moon_32
    },
    MAINLY_CLEAR {
        override val stringResId = R.string.mainly_clear
        override val dayImageResId: Int = R.drawable.ic_weather_2_32
        override val nightImageResId: Int = R.drawable.ic_weather_3_32
    },
    PARTLY_CLOUDY {
        override val stringResId = R.string.partly_cloudy
        override val dayImageResId: Int = R.drawable.ic_weather_2_32
        override val nightImageResId: Int = R.drawable.ic_weather_3_32
    },
    OVERCAST {
        override val stringResId = R.string.overcast
        override val dayImageResId: Int = R.drawable.ic_sky_32
        override val nightImageResId: Int = R.drawable.ic_sky_32
    },
    FOG {
        override val stringResId = R.string.fog
        override val dayImageResId: Int = R.drawable.ic_sky_32
        override val nightImageResId: Int = R.drawable.ic_sky_32
    },
    DEPOSITING_RIME_FOG {
        override val stringResId = R.string.depositing_rime_fog
        override val dayImageResId: Int = R.drawable.ic_sky_32
        override val nightImageResId: Int = R.drawable.ic_sky_32
    },
    DRIZZLE {
        override val stringResId = R.string.drizzle
        override val dayImageResId: Int = R.drawable.ic_weather_5_32
        override val nightImageResId: Int = R.drawable.ic_weather_6_32
    },
    SLIGHT_INTENSITY_RAIN {
        override val stringResId = R.string.slight_intensity_rain
        override val dayImageResId: Int = R.drawable.ic_weather_5_32
        override val nightImageResId: Int = R.drawable.ic_weather_6_32
    },
    MODERATE_INTENSITY_RAIN {
        override val stringResId = R.string.moderate_intensity_rain
        override val dayImageResId: Int = R.drawable.ic_weather_11_32
        override val nightImageResId: Int = R.drawable.ic_weather_12_32
    },
    HEAVY_INTENSITY_RAIN {
        override val stringResId = R.string.heavy_intensity_rain
        override val dayImageResId: Int = R.drawable.ic_weather_9_32
        override val nightImageResId: Int = R.drawable.ic_weather_7_32
    },
    LIGHT_INTENSITY_FREEZING_RAIN {
        override val stringResId = R.string.light_intensity_freezing_rain
        override val dayImageResId: Int = R.drawable.ic_weather_14_32
        override val nightImageResId: Int = R.drawable.ic_weather_15_32
    },
    HEAVY_INTENSITY_FREEZING_RAIN {
        override val stringResId = R.string.heavy_intensity_freezing_rain
        override val dayImageResId: Int = R.drawable.ic_weather_11_32
        override val nightImageResId: Int = R.drawable.ic_weather_12_32
    },
    SLIGHT_INTENSITY_SNOW {
        override val stringResId = R.string.slight_intensity_snow
        override val dayImageResId: Int = R.drawable.ic_snow_32
        override val nightImageResId: Int = R.drawable.ic_snow_32
    },
    MODERATE_INTENSITY_SNOW {
        override val stringResId = R.string.moderate_intensity_snow
        override val dayImageResId: Int = R.drawable.ic_snow_32
        override val nightImageResId: Int = R.drawable.ic_snow_32
    },
    HEAVY_INTENSITY_SNOW {
        override val stringResId = R.string.heavy_intensity_snow
        override val dayImageResId: Int = R.drawable.ic_snow_32
        override val nightImageResId: Int = R.drawable.ic_snow_32
    },
    SNOW_GRAINS {
        override val stringResId = R.string.snow_grains
        override val dayImageResId: Int = R.drawable.ic_weather_44_32
        override val nightImageResId: Int = R.drawable.ic_weather_45_32
    },
    RAIN_SHOWERS {
        override val stringResId = R.string.rain_showers
        override val dayImageResId: Int = R.drawable.ic_weather_32_32
        override val nightImageResId: Int = R.drawable.ic_weather_33_32
    },
    SNOW_SHOWERS {
        override val stringResId = R.string.snow_showers
        override val dayImageResId: Int = R.drawable.ic_weather_44_32
        override val nightImageResId: Int = R.drawable.ic_weather_45_32
    },
    SLIGHT_THUNDERSTORM {
        override val stringResId = R.string.slight_thunderstorm
        override val dayImageResId: Int = R.drawable.ic_weather_17_32
        override val nightImageResId: Int = R.drawable.ic_weather_18_32
    },
    THUNDERSTORM_WITH_HAIL {
        override val stringResId = R.string.thunderstorm_with_hail
        override val dayImageResId: Int = R.drawable.ic_weather_44_32
        override val nightImageResId: Int = R.drawable.ic_weather_45_32
    },
    UNKNOWN {
        override val stringResId = R.string.unknown
        override val dayImageResId: Int = R.drawable.ic_sun_32
        override val nightImageResId: Int = R.drawable.ic_moon_32
    };

    companion object {

        fun fromWeatherCode(code: Int): TranslatedWeatherCode = when (code) {
            0 -> CLEAR_SKY
            1 -> MAINLY_CLEAR
            2 -> PARTLY_CLOUDY
            3 -> OVERCAST
            45 -> FOG
            48 -> DEPOSITING_RIME_FOG
            51 -> DRIZZLE
            53 -> DRIZZLE
            55 -> DRIZZLE
            56 -> DRIZZLE
            57 -> DRIZZLE
            61 -> SLIGHT_INTENSITY_RAIN
            63 -> MODERATE_INTENSITY_RAIN
            65 -> HEAVY_INTENSITY_RAIN
            66 -> LIGHT_INTENSITY_FREEZING_RAIN
            67 -> HEAVY_INTENSITY_FREEZING_RAIN
            71 -> SLIGHT_INTENSITY_SNOW
            73 -> MODERATE_INTENSITY_SNOW
            75 -> HEAVY_INTENSITY_SNOW
            77 -> SNOW_GRAINS
            80 -> RAIN_SHOWERS
            81 -> RAIN_SHOWERS
            82 -> RAIN_SHOWERS
            85 -> SNOW_SHOWERS
            86 -> SNOW_SHOWERS
            95 -> SLIGHT_THUNDERSTORM
            96 -> THUNDERSTORM_WITH_HAIL
            99 -> THUNDERSTORM_WITH_HAIL
            else -> UNKNOWN
        }
    }

    abstract val stringResId: Int
    abstract val dayImageResId: Int
    abstract val nightImageResId: Int
}
