package com.loodmeet.weatherapp.core.exceptions

class ResponseIsNotSuccessfulException(
    override val cause: Throwable? = null, override val message: String? = null
) : Exception()