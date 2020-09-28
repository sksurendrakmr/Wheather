package com.surendra.wheather

//This class hold a dailyForecast item. This class represent a simple piece of data and this data won't
//change as we are not going to add functionality to it so we can convert this into a data class

//data class take at least one property in the constructor
//property is simply the variable of the class and here this variable will hold the current temperature
//here description property will hold the description

data class DailyForecast(val temp:Float, val description:String)



