package com.surendra.wheather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//sealed class represent different type of location
sealed class Location {
    data class ZipCode(val zipCode:String):Location()

}

private const val KEY_ZIPCODE="key_zipcode"
class LocationRepository(context: Context) {
    private val preferences=context.getSharedPreferences("settings",Context.MODE_PRIVATE)

    private val _savedLocation:MutableLiveData<Location> = MutableLiveData()
    val savedLocation:LiveData<Location> =_savedLocation

    //need to know if the location changes (explanation below)
    init {
        //whenever sharedPreferences change, we are gonna notify
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key != KEY_ZIPCODE) return@registerOnSharedPreferenceChangeListener
            broadcastSavedZipcode()

        }
        //notifying our observers about the current values
        broadcastSavedZipcode()
    }

    fun saveLocation(location: Location){
        when(location){
            is Location.ZipCode->preferences.edit().putString(KEY_ZIPCODE,location.zipCode).apply()
        }
    }

    private fun broadcastSavedZipcode(){
        val zipCode=preferences.getString(KEY_ZIPCODE,"")
        if (zipCode!=null && zipCode.isNotBlank()){
            _savedLocation.value=Location.ZipCode(zipCode)
        }
    }
}

//Here we are saving the zipcode when we enter the zipcode

//We have created this sealed class hierarchy of location that enumerates all the
//different kind of location we handle in the app and then we have added zipCode as
//a specific type

//need to know if the location changes so that we can notify other callers as this location could change
//because other instances of this class may be we are gonna change it or may be on disk could even change
//it. so we are listen for updates for that so we can notify our observers