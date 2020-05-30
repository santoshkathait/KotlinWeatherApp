package com.kat.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kat.weather.BR
import com.kat.weather.R
import com.kat.weather.Utils.ToolsUtils
import com.kat.weather.databinding.AdapterWeatherListBinding
import com.kat.weather.model.ResponseData

/**
 * Weather adapter class for dispaly the item in recycler view
 */
class WeatherListAdapter(private val weatherList: MutableList<ResponseData>) :
    RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {
    private var onItemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding: AdapterWeatherListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.adapter_weather_list, parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weatherList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView, position)
        }
    }

    /**
     * View holder clas fo recycler binding
     *
     * @param binding databinding class object
     */
    inner class ViewHolder(private val binding: AdapterWeatherListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {
            binding.setVariable(BR.data, item)
            binding.executePendingBindings()
        }
    }

    /**
     * Method to set on item click listener
     *
     * @param clickListener Listener type
     */
    fun setItemClickListener(clickListener: ItemClickListener) {
        onItemClickListener = clickListener
    }

    /**
     * Interface for weather on click listener
     */
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    /**
     * Object for binding adapter's data
     */
    companion object {
        @JvmStatic
        @BindingAdapter("bindServerDate")
        fun bindServerDate(@NonNull textView: TextView, date: String) {
            textView.text = ToolsUtils.getDateTime(date)
        }
    }
}

