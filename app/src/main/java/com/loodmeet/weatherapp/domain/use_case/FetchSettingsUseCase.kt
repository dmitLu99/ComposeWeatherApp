package com.loodmeet.weatherapp.domain.use_case

import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.Settings
import com.loodmeet.weatherapp.data.Repository
import com.loodmeet.weatherapp.di.AppScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AppScope
class FetchSettingsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): Settings {

        return repository.fetchSettings()
    }
}