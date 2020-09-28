package com.surendra.wheather.details

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.surendra.wheather.*

class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var  tempDisplaySettingManager:TempDisplaySettingManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        tempDisplaySettingManager= TempDisplaySettingManager(this)

        setTitle(R.string.forecast_detail_title)

        val tvDetailsActivityTemp=findViewById<TextView>(R.id.tvDetailsActivityTemp)
        val tvDetailsActivityDescription=findViewById<TextView>(R.id.tvDetailsActivityDescription)

        val temp=intent.getFloatExtra("key_temp",0f)
        val description=intent.getStringExtra("key_description")

        tvDetailsActivityTemp.text= formatTempToDisplay(temp,tempDisplaySettingManager.getTempDisplaySetting())
        tvDetailsActivityDescription.text=description
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater=menuInflater
        inflater.inflate(R.menu.settings_menu,menu)
        return true //indicates we handle the menu and we want to show the items
    }

    //to handle click on menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.tempDisplaySetting-> {
                //do something
                showTempDisplaySettingDialog(this,tempDisplaySettingManager)
                true //indicates that we handle the click
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    //function to show dialog
//    private fun showTempDisplaySettingDialog(){
//        val alertDialog=AlertDialog.Builder(this)
//            .setTitle("Choose Display Units")
//            .setMessage("Choose which temperature nit to use for temperature display")
//            .setPositiveButton("F°"){_,_->
//                //_,_ means here the clickHandler takes two parameters but we are not gonna use it
//                tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
//            }
//            .setNeutralButton("C°", object : DialogInterface.OnClickListener{
//                override fun onClick(p0: DialogInterface?, p1: Int) {
//                    //Toast.makeText(this@ForecastDetailsActivity,"Show using C°",Toast.LENGTH_SHORT).show()
//                    tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
//
//                }
//
//            })
//            .setOnDismissListener {
//                Toast.makeText(this,"Setting will take effect on app restart",Toast.LENGTH_SHORT).show()
//            }
//            .show()
//    }
}

//dismiss listeners will let us do something whenever the dialog is closed
//In our case we will tell the user that their display setting will take effect after they
//restart the app

//lateinit var means this is a variable that at some point will be assigned a value but it isn't
//going to assigned right now