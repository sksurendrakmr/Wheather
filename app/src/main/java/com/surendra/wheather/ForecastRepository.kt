package com.surendra.wheather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.surendra.wheather.api.CurrentWeather
import com.surendra.wheather.api.WeeklyForecast
import com.surendra.wheather.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.util.*
import kotlin.random.Random

//Repository essentially doing two things -> Loading data for us and providing that data to our activity/fragment

class ForecastRepository {

    private val _currentWeather=MutableLiveData<CurrentWeather>()
    val currentWeather:LiveData<CurrentWeather> =_currentWeather

    private val _weeklyForecast=MutableLiveData<WeeklyForecast>() //used this property to update the data
    val weeklyForecast :LiveData<WeeklyForecast> = _weeklyForecast

    //loading the data
    fun loadWeeklyForecast(zipCode:String){

        //for weekly forecast, the api doesn't provide zipcode, it provide lan and long
        //so first we make a call to the currentWeather endPoint as it will return us lat nad long
        //then we use lat and long to call the second endPoint and get the seven day forecast

        val call= createOpenWeatherMapService().currentWeather(zipCode,"imperial",BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather>{
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse=response.body()
                if (weatherResponse!=null){
                    //load seven day forecast
                    val forecastCall= createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.lon,
                        exclude ="current,minutely,hourly",
                        units = "imperial",
                        apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )
                    forecastCall.enqueue(object:Callback<WeeklyForecast>{
                        override fun onResponse(
                            call: Call<WeeklyForecast>,
                            response: Response<WeeklyForecast>
                        ) {
                            val weeklyForecastResponse=response.body()
                            if (weeklyForecastResponse!=null){
                                _weeklyForecast.value=weeklyForecastResponse
                            }
                        }

                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName,"Error loading weekly forecast",t)
                        }

                    })
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName,"Error while loading location for weekly forecast",t)
            }
        })

    }

    fun loadCurrentForecast(zipcode:String) {
       //creating a call class that represents that request to the endpoint we want
        val call = createOpenWeatherMapService().currentWeather(zipcode,"imperial",BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        //using this call to get response back
        call.enqueue(object :Callback<CurrentWeather> {
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse=response.body()
                if (weatherResponse != null){
                    _currentWeather.value=weatherResponse
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName,"Error loading current weather",t)
            }

        })

    }

    private fun getTempDescription(temp:Float):String{
         return when(temp) {
             in Float.MIN_VALUE.rangeTo(0f)->"Anything below 0 doesn't make sense"
             in 0f.rangeTo(32f) -> "Way too cold"
             in 32f.rangeTo(55f)->"Colder than I would prefer"
             in 55f.rangeTo(65f) ->"Getting better"
             in 65f.rangeTo(80f) ->"That's the sweet spot"
             in 80f.rangeTo(90f)->"Getting little warm"
             in 90f.rangeTo(100f)->"Where's the A/C"
             in 100f.rangeTo(Float.MAX_VALUE)->"What is this, Arizona?"
             else ->"Does not compute"
         }
    }
}

//weeklyForecast is public so activity get access to it and it's a livedata instead of mutableLiveData
//means anything references or listening it like our mainActivity, will able to get updates but not
//to publish it it's own changes and this is very important because we want the repository to be the
//only place that can modify this data

//rem (reminder) is like modulus operator -->by calling rem on the random function all the values generated
//will be between 0 - 1 when we mutiply by 100 the values will be between 0-100

//Thus we can change how the repository data is created and loaded but it wouldn't matter i.e the
//rest of the code won't have to know how it changes which make them nicely decoupled and we can work on
//them somewhat independently