package com.surendra.wheather.api

import com.surendra.wheather.Location
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


fun createOpenWeatherMapService():OpenWeatherMapService{
    val retrofit=Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(OpenWeatherMapService::class.java) //create implementation of our api and we are able to use that api class to return the weather data
}

//This interface will serve as basis retrofit api service
interface OpenWeatherMapService {

    @GET("/data/2.5/weather")
    fun currentWeather(
        @Query("zip") zipCode:String,
        @Query("units") units:String,
        @Query("appid") apiKey:String
    ):Call<CurrentWeather>

    @GET("/data/2.5/onecall")
    fun sevenDayForecast(
        @Query("lat") lat:Float,
        @Query("lon") lon:Float,
        @Query("exclude") exclude:String,
        @Query("units") units:String,
        @Query("appid") apiKey:String
    ):Call<WeeklyForecast>
}

//we will use retrofit Call class to wrap the response and also we need to tell what types we expect back
