package com.surendra.wheather.api

import com.squareup.moshi.Json

//we are going to define the model needed to model the api response from weather endpoint

data class Forecast(val temp:Float)
data class Coordinates(val lat:Float,val lon:Float)

data class CurrentWeather(
    val name:String,
    val coord:Coordinates,
    @field: Json(name="main") val forecast: Forecast
)