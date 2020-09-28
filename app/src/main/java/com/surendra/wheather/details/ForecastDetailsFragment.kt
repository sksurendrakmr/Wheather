package com.surendra.wheather.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.surendra.wheather.*

class ForecastDetailsFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val args:ForecastDetailsFragmentArgs by navArgs() //delegate functions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast_details,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val tvDetailsActivityTemp = view.findViewById<TextView>(R.id.tvDetailsActivityTemp)
        val tvDetailsActivityDescription = view.findViewById<TextView>(R.id.tvDetailsActivityDescription)

        tvDetailsActivityTemp.text= formatTempToDisplay(args.temp,tempDisplaySettingManager.getTempDisplaySetting())
        tvDetailsActivityDescription.text=args.description

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


//dismiss listeners will let us do something whenever the dialog is closed
//In our case we will tell the user that their display setting will take effect after they
//restart the app

//lateinit var means this is a variable that at some point will be assigned a value but it isn't
//going to assigned right now