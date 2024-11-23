package com.lazamelezi.weatherappmvvmdagger.features.weather_info_show.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lazamelezi.weatherappmvvmdagger.common.RequestCompleteListener
import com.lazamelezi.weatherappmvvmdagger.features.weather_info_show.model.WeatherInfoShowModel
import com.lazamelezi.weatherappmvvmdagger.features.weather_info_show.model.data_class.City
import com.lazamelezi.weatherappmvvmdagger.features.weather_info_show.model.data_class.WeatherData
import com.lazamelezi.weatherappmvvmdagger.features.weather_info_show.model.data_class.WeatherInfoResponse
import com.lazamelezi.weatherappmvvmdagger.utils.kelvinToCelsius
import com.lazamelezi.weatherappmvvmdagger.utils.unixTimestampToDateTimeString
import com.lazamelezi.weatherappmvvmdagger.utils.unixTimestampToTimeString
import javax.inject.Inject

class WeatherInfoViewModel @Inject constructor(var model: WeatherInfoShowModel) : ViewModel() {


    val cityListLiveData = MutableLiveData<MutableList<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()
    val weatherInfoLiveData = MutableLiveData<WeatherData>()
    val weatherInfoFailureLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()


    fun getCityList() {

        model.getCityList(object :
            RequestCompleteListener<MutableList<City>> {
            override fun onRequestSuccess(data: MutableList<City>) {
                cityListLiveData.postValue(data) // PUSH data to LiveData object
            }

            override fun onRequestFailed(errorMessage: String) {
                cityListFailureLiveData.postValue(errorMessage) // PUSH error message to LiveData object
            }
        })
    }


    fun getWeatherInfo(cityId: Int) {

        progressBarLiveData.postValue(true) // PUSH data to LiveData object to show progress bar

        model.getWeatherInfo(cityId, object :
            RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                // business logic and data manipulation tasks should be done here
                val weatherData = WeatherData(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    weatherConditionIconDescription = data.weather[0].description,
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} KM",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                progressBarLiveData.postValue(false) // PUSH data to LiveData object to hide progress bar

                // After applying business logic and data manipulation, we push data to show on UI
                weatherInfoLiveData.postValue(weatherData) // PUSH data to LiveData object
            }

            override fun onRequestFailed(errorMessage: String) {
                progressBarLiveData.postValue(false) // hide progress bar
                weatherInfoFailureLiveData.postValue(errorMessage) // PUSH error message to LiveData object
            }
        })
    }
}