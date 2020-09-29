package com.surendra.wheather.api

import com.squareup.moshi.Json

//Like the currentWeather , we are gonna define several new models that build up the response for
//our weekly Forecast data

data class WeatherDescription(val main:String, val description:String, val icon:String)

data class Temp(val min:Float,val max:Float)

data class DailyForecast (
    @field:Json(name = "dt")val date:Long,
    val temp: Temp,
    val weather:List<WeatherDescription>
)

data class WeeklyForecast(val daily : List<DailyForecast>)
