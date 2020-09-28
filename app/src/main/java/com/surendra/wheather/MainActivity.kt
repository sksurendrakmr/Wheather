package com.surendra.wheather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.surendra.wheather.forecast.CurrentForecastFragmentDirections
import com.surendra.wheather.location.LocationEntryFragment

class MainActivity : AppCompatActivity(),AppNavigator {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository=ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager= TempDisplaySettingManager(this)

        val navController=findNavController(R.id.nav_host_fragment)
        val appBarConfiguration= AppBarConfiguration(navController.graph) //this will create appBarConfiguration based on our navigation graph
        //connecting
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController,appBarConfiguration)

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater=menuInflater
        inflater.inflate(R.menu.settings_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.tempDisplaySetting->{
                showTempDisplaySettingDialog(this,tempDisplaySettingManager)
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }


    override fun navigateToLocationEntry() {
//        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,LocationEntryFragment()).commit()

        val action=CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    override fun navigateToForecastDetail(forecast: DailyForecast) {
        val action=CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(forecast.temp,forecast.description)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

}

//lifeCycleOwner -->something that know about the current lifecycle
//observer updates the data anytime the liveData changes in the repository
//submitList -->it will let us send a new list of items which will update what's on the screen

//anytime in kotlin we are passing a function to other function, if the function is passing is the
//last argument then we can pass it outside the parentheses
//means if have constructor or function that only takes a function parameter then we can pass that
///function in from the outside of the parentheses. This is called trailing lambda syntax

//getString() is a special method on the activity that lets us get access to string resource directly

//Step1->Create data class
//step2-> create repository and define livedata and load the livedata with the values
//Step3->In the activity/fragment, start observing these values that are emitted by liveData


//for navigate from one screen to other using navigation architectural components,
//first we define actions that will used by the navController to decide what fragment to show next