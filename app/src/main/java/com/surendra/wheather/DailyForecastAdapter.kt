package com.surendra.wheather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.surendra.wheather.api.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT=SimpleDateFormat("MM-dd-yyyy")

class DailyForecastViewHolder(view: View, private val tempDisplaySettingManager: TempDisplaySettingManager) : RecyclerView.ViewHolder(view){
//viewHolder is responsible for binding the individual view
    private val tvTemp=view.findViewById<TextView>(R.id.tvTemp)
    private val tvdescription : TextView=view.findViewById(R.id.tvDescription)
    private val dateText=view.findViewById<TextView>(R.id.dateText)
    private val forecastIcon=view.findViewById<ImageView>(R.id.forecastIcon)

    //this method is called from adapter onBind method and it's where we are going to connect individual daily forecastItem to those views we just referenced
    fun bind(dailyForecast: DailyForecast){
        tvTemp.text= formatTempToDisplay(dailyForecast.temp.max,tempDisplaySettingManager.getTempDisplaySetting())
        tvdescription.text=dailyForecast.weather[0].description
        dateText.text= DATE_FORMAT.format(Date(dailyForecast.date*1000))

        val iconId=dailyForecast.weather[0].icon
        forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
    }
}


class DailyForecastAdapter(private val tempDisplaySettingManager: TempDisplaySettingManager,
                        private val clickHandler:(DailyForecast)->Unit
) : ListAdapter<DailyForecast,DailyForecastViewHolder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG=object :DiffUtil.ItemCallback<DailyForecast>(){
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                return oldItem===newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem==newItem  //using == we will return true if the contents of the item are same but it doesn't have to be the same object
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        //it create a new viewHolder and within that viewHolder we will create a new View to represent each item in the list
        val iteView=LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast,parent,false)
        return DailyForecastViewHolder(iteView,tempDisplaySettingManager) //anytime the recyclerView needs to create a new ViewHolder it's gonna call onCreateViewHolder method
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        //Get each individual element of the forecastItems and pass that data along to the viewHolder
        //so the view Items can be updated
        //Any times we need to buy the viewHolder so that it can put new information on the screen, it's gonna called onBindViewHolder()
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            clickHandler(getItem(position))
        }
    }


}








//ListAdapter will take two parameters->1. the type of item that will be passed into this ListAdapter
//2. type of viewHolder that will be used to bind those items to layouts

//ListAdapter class has a constructor and that constructor requires the instance of a class called
//a item callback. ItemCallback is a utility class used to make our recyclerView binding a little
//bit more efficient

//object means we are creating a new instance of an unnamed class(inner class)
//=== in kotlin means whether or not oldItem and newItem are the exact same reference

//click handling in the recyclerView
//Step1- we want to find out a parameter on our adapter that will define the function to be called
//whenever a listItem is clicked
