package com.lazamelezi.weatherappmvvmdagger.features.weather_info_show.model

import com.lazamelezi.weatherappmvvmdagger.common.RequestCompleteListener
import com.lazamelezi.weatherappmvvmdagger.features.weather_info_show.model.data_class.City
import com.lazamelezi.weatherappmvvmdagger.features.weather_info_show.model.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}