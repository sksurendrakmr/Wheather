package com.surendra.wheather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.DecimalFormat
import kotlin.random.Random

//Repository essentially doing two things -> Loading data for us and providing that data to our activity/fragment

class ForecastRepository {

    private val _weeklyForecast=MutableLiveData<List<DailyForecast>>() //used this property to update the data
    val weeklyForecast :LiveData<List<DailyForecast>> = _weeklyForecast

    //loading the data
    fun loadForecast(zipCode:String){

        val randomValues= List(7){
            //how the seven item will be created
//            val df=DecimalFormat("#.##")
//            df.format(Random.nextFloat().rem(100)*100).toFloat()
            Random.nextFloat().rem(100)*100
        }
        val forecastItems=randomValues.map {temp->
            DailyForecast(temp,getTempDescription(temp))
        }
        _weeklyForecast.value=forecastItems
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