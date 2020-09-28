package com.surendra.wheather

import android.content.Context


enum class TempDisplaySetting {
    Fahrenheit,
    Celsius
}
class TempDisplaySettingManager(context:Context) {

    private val preferences=context.getSharedPreferences("settings",Context.MODE_PRIVATE)

    fun updateSetting(setting: TempDisplaySetting){
        preferences.edit().putString("key_temp_display",setting.name).apply()
    }
    fun getTempDisplaySetting():TempDisplaySetting{
        val settingValue=preferences.getString("key_temp_display",TempDisplaySetting.Fahrenheit.name) ?: TempDisplaySetting.Fahrenheit.name
        return TempDisplaySetting.valueOf(settingValue)
    }
}





//TempDisplaySettingManager references shared preferences to save the temperature display setting value
//getSharedPreferences is essentially opening a file in the disk or creating it if it doesn't exist and
//it's gonna allow us to write out key value-pair of data to this file using a very simple API
//and this is kind of a default simple way of storing bits of data that we want to persist across
//application restarts

//TempDisplaySetting.valueOf(settingValue) -> This making use of valueOf method of an enum which basically
//say we can pass in a string to valueOf and if that string matches the name of one of the enum types
//(in our case Fahrenheit or celsius) then it will basically return us back the instance of that