package com.surendra.wheather

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempToDisplay(temp:Float,tempDisplaySetting: TempDisplaySetting):String {
   return when(tempDisplaySetting){
       TempDisplaySetting.Fahrenheit -> String.format("%.2fF°",temp)
       TempDisplaySetting.Celsius -> {
           val temp=(temp-32f)*(5f/9f)
           String.format("%.2fC°",temp)
       }
   }
}


 fun showTempDisplaySettingDialog(context: Context,tempDisplaySettingManager: TempDisplaySettingManager){
    val alertDialog= AlertDialog.Builder(context)
        .setTitle("Choose Display Units")
        .setMessage("Choose which temperature nit to use for temperature display")
        .setPositiveButton("F°"){_,_->
            //_,_ means here the clickHandler takes two parameters but we are not gonna use it
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        .setNeutralButton("C°", object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                //Toast.makeText(this@ForecastDetailsActivity,"Show using C°",Toast.LENGTH_SHORT).show()
                tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)

            }

        })
        .setOnDismissListener {
            Toast.makeText(context,"Setting will take effect on app restart", Toast.LENGTH_SHORT).show()
        }
        .show()
}