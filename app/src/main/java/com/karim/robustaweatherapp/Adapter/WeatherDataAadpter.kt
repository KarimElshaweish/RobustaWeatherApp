package com.karim.robustaweatherapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.karim.robustaweatherapp.R
import com.karim.robustaweatherapp.databinding.ItemViewLayoutBinding
import com.karim.robustaweatherapp.model.Weather.WeatherData

class WeatherDataAadpter(var weatherData:List<WeatherData>) : RecyclerView.Adapter<WeatherDataAadpter.ViewHolder>() {
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
        return 20
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }
}