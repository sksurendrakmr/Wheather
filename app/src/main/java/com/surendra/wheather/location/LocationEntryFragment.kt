package com.surendra.wheather.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.surendra.wheather.AppNavigator
import com.surendra.wheather.R



class LocationEntryFragment : Fragment() {


    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // we are assigning appNavigator to the value of context,thus appNavigator will now have a reference
        // to our activity and for some reason our activity doesn't implement AppNavigator, this will crash when
        // we try to run it. Thus quickly tell us we miss something in our code
        appNavigator=context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_location_entry, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val etEnterZipCode: EditText =view.findViewById(R.id.etEnterZipCode)
        val btnSubmitZipCode: Button =view.findViewById(R.id.btnSubmitZipCode)

        btnSubmitZipCode.setOnClickListener {
            val zipCode:String=etEnterZipCode.text.toString().trim()
            if (zipCode.length<5){
                Toast.makeText(requireContext(),"Please fill valid ZipCode", Toast.LENGTH_SHORT).show()
            }
            else{
//                Toast.makeText(requireContext(),"Button Clicked $zipCode",Toast.LENGTH_SHORT).show()
              findNavController().navigateUp()
            }
        }

    }

}

//LocationEntryFragment is the controller for this location entry, the thing that are responsible
//for showing the layout, also responsible for handling the click and responsible for telling the
//activity that we should navigate and show the forecast data


//onAttach-->when fragment is added to the activity, this is our first point where we can get some
//type of reference to the activity or the context.
//since fragment have to live with an activity and we expect our activity to implement this app navigator
//interface so we can assume this context is our appNavigator