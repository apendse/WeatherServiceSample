package com.aap.weather.govweather.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aap.weather.govweather.R
import com.aap.weather.govweather.databinding.WeatherRowBinding
import com.aap.weather.govweather.ui.main.data.PeriodDataVO
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import java.text.SimpleDateFormat

class WeatherForecastAdapter: RecyclerView.Adapter<HourlyDataVH>() {
    private val list = mutableListOf<PeriodDataVO>()

    fun setData(listParam: List<PeriodDataVO>) {
        list.clear()
        list.addAll(listParam)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyDataVH {
        val binding = WeatherRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyDataVH(binding)
    }

    private fun getTimeString(context: Context, periodDataVO: PeriodDataVO): String {
        //2022-04-08T22:00:00-07:00
        val from = SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ssZ")
        val startDate = from.parse(periodDataVO.startTime)
        val endDate = from.parse(periodDataVO.endTime)
        val to = SimpleDateFormat("hh:mm a")
        val startStr = to.format(startDate)
        val endStr = to.format(endDate)
        return "from $startStr \nto $endStr"
    }

    override fun onBindViewHolder(holder: HourlyDataVH, position: Int) {
        val periodDataVO = list[position]
        holder.binding.forecast.text = periodDataVO.shortForecast
        holder.binding.temperature.text = holder.itemView.resources.getString(R.string.temperature_template, periodDataVO.temperature)
        holder.binding.time.text = getTimeString(holder.itemView.context, periodDataVO)
        val imageUrl = periodDataVO.icon
        if (imageUrl.isNullOrBlank()) {
            Glide.with(holder.binding.weatherThumbnailImage.context).clear(holder.binding.weatherThumbnailImage)
        } else {
            val theImage = GlideUrl(
                imageUrl, LazyHeaders.Builder()
                    .addHeader("User-Agent", "Android")
                    .build()
            )
            Glide.with(holder.binding.weatherThumbnailImage.context).load(theImage)
                .into(holder.binding.weatherThumbnailImage)
        }
    }

    override fun getItemCount(): Int  {
        return list.size
    }

}


class HourlyDataVH(val binding: WeatherRowBinding) : RecyclerView.ViewHolder(binding.root)