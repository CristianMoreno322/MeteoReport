package basedatos.movil.meteoreport.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import basedatos.movil.meteoreport.data.local.AppDatabase
import basedatos.movil.meteoreport.model.WeatherReport
import basedatos.movil.meteoreport.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository

    val allReports = dao().also { dao ->
        repository = WeatherRepository(dao)
    }.let { repository.getAllReports() }

    private fun dao() = AppDatabase.getInstance(getApplication()).weatherDao()

    fun insertReport(report: WeatherReport) {
        viewModelScope.launch { repository.insertReport(report) }
    }

    fun updateReport(report: WeatherReport) {
        viewModelScope.launch { repository.updateReport(report) }
    }

    fun deleteReport(report: WeatherReport) {
        viewModelScope.launch { repository.deleteReport(report) }
    }

    fun fetchWeather(city: String) {
        viewModelScope.launch { repository.fetchAndSaveWeather(city) }
    }
}