package basedatos.movil.meteoreport.repository

import basedatos.movil.meteoreport.data.local.WeatherDao
import basedatos.movil.meteoreport.data.remote.RetrofitClient
import basedatos.movil.meteoreport.model.WeatherReport

class WeatherRepository(private val dao: WeatherDao) {

    fun getAllReports() = dao.getAllReports()

    suspend fun insertReport(report: WeatherReport) = dao.insertReport(report)

    suspend fun updateReport(report: WeatherReport) = dao.updateReport(report)

    suspend fun deleteReport(report: WeatherReport) = dao.deleteReport(report)

    suspend fun fetchAndSaveWeather(city: String) {
        try {
            val response = RetrofitClient.api.getCurrentWeather(city)
            val report = WeatherReport(
                city = response.name,
                country = response.sys.country,
                temperature = response.main.temp,
                humidity = response.main.humidity,
                windSpeed = response.wind.speed,
                pressure = response.main.pressure,
                weatherMain = response.weather[0].main,
                description = response.weather[0].description,
                iconCode = response.weather[0].icon,
                timestamp = response.dt,
                isSynced = true
            )
            dao.insertReport(report)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}