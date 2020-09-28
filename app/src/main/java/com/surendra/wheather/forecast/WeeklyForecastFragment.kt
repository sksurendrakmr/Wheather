package com.surendra.wheather.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.surendra.wheather.*
import com.surendra.wheather.details.ForecastDetailsFragment


class WeeklyForecastFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository= ForecastRepository()
    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator=context as AppNavigator
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_forecast, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val zipcode= arguments?.getString(KEY_ZIPCODE)?:""
        tempDisplaySettingManager= TempDisplaySettingManager(requireContext())

        //RecyclerView
        val rvForecastList: RecyclerView =view.findViewById(R.id.rvForecastList)
        rvForecastList.layoutManager= LinearLayoutManager(requireContext())
        val dailyForecastAdapter= DailyForecastAdapter(tempDisplaySettingManager){ forecast->
            //this is where we handle our click feedback
//            val msg=getString(R.string.forecast_clicked_format,it.temp,it.description)
//            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
            showForeCastDetails(forecast)

        }
        rvForecastList.adapter=dailyForecastAdapter


        //observing the data from LiveData(adding the observer to this repository, the observer is let us know when updates are made)
        val weeklyForecastObserver= Observer<List<DailyForecast>>{ forecastItem->
            //items available in this lambda is forecastItems(defined in repository)
            //we will update the recyclerview adapter  with this forecastItem
            dailyForecastAdapter.submitList(forecastItem)
        }
        forecastRepository.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)
        forecastRepository.loadForecast(zipcode)


        val locationEntryButton=view.findViewById<FloatingActionButton>(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }
    }

    private fun showForeCastDetails(forecast:DailyForecast){
        val action=WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(forecast.temp,forecast.description)
        findNavController().navigate(action)
    }



    companion object {
        const val KEY_ZIPCODE="key_zipcode"

        fun newInstance(zipcode:String):WeeklyForecastFragment{
            val fragment=WeeklyForecastFragment()
            val args=Bundle()
            args.putString(KEY_ZIPCODE,zipcode)
            fragment.arguments=args
            return fragment
        }
    }
}

//A bundle is a class defined to store key-value pair in android, we use bundle to pass things
//in intents or to pass them around with fragment argument