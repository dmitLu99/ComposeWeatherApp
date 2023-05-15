package com.loodmeet.weatherapp.domain.use_case

import com.loodmeet.weatherapp.core.models.Location
import javax.inject.Inject

class FetchLocationUseCase @Inject constructor() {

    suspend fun execute(): Location {

        return Location.Moscow
    }
}