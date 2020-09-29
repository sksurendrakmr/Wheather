package com.surendra.wheather.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.surendra.wheather.*
import com.surendra.wheather.api.CurrentWeather
import com.surendra.wheather.details.ForecastDetailsFragment


class CurrentForecastFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository= ForecastRepository()
    private lateinit var appNavigator: AppNavigator
    private lateinit var locationRepository: LocationRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator=context as AppNavigator
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_forecast, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val zipcode= arguments?.getString(KEY_ZIPCODE)?:""
        tempDisplaySettingManager= TempDisplaySettingManager(requireContext())
        val locationName=view.findViewById<TextView>(R.id.locationName)
        val tempText=view.findViewById<TextView>(R.id.tempText)

        //observing the data from LiveData(adding the observer to this repository, the observer is let us know when updates are made)

        val currentWeatherObserver=Observer<CurrentWeather> {weather->
            locationName.text = weather.name
            tempText.text= formatTempToDisplay(weather.forecast.temp,tempDisplaySettingManager.getTempDisplaySetting())
        }

        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        val locationEntryButton=view.findViewById<FloatingActionButton>(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }

        locationRepository= LocationRepository(requireContext())
        val savedLocationObserver=Observer<Location> {savedLocation->
            when(savedLocation){
                is Location.ZipCode->forecastRepository.loadCurrentForecast(savedLocation.zipCode)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)
    }

//    private fun showForeCastDetails(forecast:DailyForecast){
//        appNavigator.navigateToForecastDetail(forecast)
//    }

    companion object {
        const val KEY_ZIPCODE="key_zipcode"

        fun newInstance(zipcode:String):CurrentForecastFragment{
            val fragment=CurrentForecastFragment()
            val args=Bundle()
            args.putString(KEY_ZIPCODE,zipcode)
            fragment.arguments=args
            return fragment
        }
    }
}

//A bundle is a class defined to store key-value pair in android, we use bundle to pass things
//in intents or to pass them around with fragment argument