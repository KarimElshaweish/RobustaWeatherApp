package com.karim.robustaweatherapp.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.karim.robustaweatherapp.R
import com.karim.robustaweatherapp.databinding.ItemViewLayoutBinding
import com.karim.robustaweatherapp.model.Weather.WeatherData
import com.karim.robustaweatherapp.model.roomcashing.OfflineWeather
import com.karim.robustaweatherapp.view.MainActivity
import kotlinx.android.synthetic.main.item_view_layout.view.*

class WeatherDataAadpter(var weatherData:List<OfflineWeather>) : RecyclerView.Adapter<WeatherDataAadpter.ViewHolder>() {
    class ViewHolder( itemView: ItemViewLayoutBinding) :RecyclerView.ViewHolder(itemView.root) {
    }
    var context: Context?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context=parent.context
        val itemViewBinding:ItemViewLayoutBinding=DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_view_layout,parent,false)
        return ViewHolder(itemViewBinding)
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offlineWeather=weatherData[position]
        holder.itemView.itemTemp.text=offlineWeather.temp
        holder.itemView.itemHumidity.text=offlineWeather.humidity
        holder.itemView.itemCityName.text=offlineWeather.place
        val bitmap= BitmapFactory.decodeFile(offlineWeather.imagePath)
        holder.itemView.thumbilImageView.setImageBitmap(bitmap)
        holder.itemView.allLayout.setOnClickListener{
            val intent=Intent(context,MainActivity::class.java)
            intent.putExtra("adapter",true)
            intent.putExtra("place",offlineWeather.place)
            intent.putExtra("humidity",offlineWeather.humidity)
            intent.putExtra("temp",offlineWeather.temp)
            intent.putExtra("image",offlineWeather.imagePath)
            context!!.startActivity(intent)

        }
    }
    fun setNewList(newList:List<OfflineWeather>){
        this.weatherData=newList
        notifyDataSetChanged()
    }
}