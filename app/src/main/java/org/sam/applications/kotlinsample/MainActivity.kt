package org.sam.applications.kotlinsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.sam.applications.kotlinsample.extensions.PreferenceExtension
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class MainActivity : AppCompatActivity(), ToolbarManager, AnkoLogger {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    val zipCode: Long by PreferenceExtension(this, SettingsActivity.ZIPCODE, SettingsActivity.DEFAULT_ZIPCODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
    }

    override fun onResume() {
        super.onResume()
        loadWeatherData()
    }

    fun loadWeatherData() = async(UI) {
        val waiter = bg { WeatherApi(zipCode).execute() }
        showData(waiter.await())
    }

    fun showData(result: ForecastResult) {
        weatherList.layoutManager = LinearLayoutManager(this);
        weatherList.adapter = WeatherRecyclerAdapter(result.list) {
            startActivity<DetailActivity>(DetailActivity.NAME to result.city.name,
                    DetailActivity.DESC to it.weather[0].description,
                    DetailActivity.MIN_TEMP to it.temp.min,
                    DetailActivity.MAX_TEMP to it.temp.max,
                    DetailActivity.ICON to it.weather[0].icon,
                    DetailActivity.DATE to it.dt)

        }
        toolbarTitle = "${result.city.name} (${result.city.country})"
    }
}
