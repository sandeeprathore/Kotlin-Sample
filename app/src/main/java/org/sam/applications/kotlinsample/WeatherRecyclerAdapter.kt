package org.sam.applications.kotlinsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_list_item.view.*
import org.sam.applications.kotlinsample.extensions.toStringDate


class WeatherRecyclerAdapter(val items: List<Forecast>, val callback: (Forecast) -> Unit) : RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: WeatherRecyclerAdapter.ViewHolder, position: Int) {
        holder.showData(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherRecyclerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_list_item, parent, false),
                callback)
    }

    class ViewHolder(val view: View, val callback: (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {
        fun showData(forecast: Forecast) {
            with(forecast) {
                Picasso.with(itemView.context).load(String.format(WeatherApi.IMAGE_URL, weather[0].icon)).into(itemView.imgWeather)
                itemView.txtDay.text = (dt*1000).toStringDate()
                itemView.txtDesc.text = weather[0].description
                with(temp) {
                    itemView.txtMinWeather.text = min.toString()
                    itemView.txtMaxWeather.text = max.toString()
                }
                itemView.setOnClickListener { callback(this) }
            }
        }
    }
}