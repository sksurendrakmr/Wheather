package com.surendra.wheather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surendra.wheather.details.ForecastDetailsActivity
import com.surendra.wheather.forecast.CurrentForecastFragment
import com.surendra.wheather.location.LocationEntryFragment

class MainActivity : AppCompatActivity(),AppNavigator {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository=ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager= TempDisplaySettingManager(this)

        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,LocationEntryFragment()).commit()
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

    override fun navigateToCurrentForecast(zipCode: String) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,CurrentForecastFragment.newInstance(zipCode))
            .addToBackStack("")
            .commit()
    }

    override fun navigateToLocationEntry() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,LocationEntryFragment()).commit()
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