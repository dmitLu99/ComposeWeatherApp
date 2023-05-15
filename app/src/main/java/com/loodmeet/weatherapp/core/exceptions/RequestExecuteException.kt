package com.loodmeet.weatherapp.core.exceptions

class RequestExecuteException(
    override val cause: Throwable? = null, override val message: String? = null
) : Exception()