package com.surendra.wheather

interface AppNavigator {


    fun navigateToLocationEntry()

    fun navigateToForecastDetail(forecast: DailyForecast)
}



//this interface going to signify a way for us to indicate if we want to navigate from one screen
//to other

//we need to have some class that implement navigateToCurrentForecast() function and defines this behaviour
//so that if we call navigateToCurrentForecast() we will navigate to forecast
//(here mainActivity will implement this function)