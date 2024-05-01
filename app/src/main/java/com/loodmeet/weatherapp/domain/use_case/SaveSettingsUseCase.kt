package com.loodmeet.weatherapp.domain.use_case

import com.loodmeet.weatherapp.core.models.Settings
import com.loodmeet.weatherapp.data.Repository
import com.loodmeet.weatherapp.di.AppScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AppScope
class SaveSettingsUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend fun execute(settings: Settings) = withContext(Dispatchers.Default) {
        repository.saveSettings(settings)
    }
}