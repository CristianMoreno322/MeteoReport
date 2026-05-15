package basedatos.movil.meteoreport.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_reports")
data class WeatherReport(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val country: String = "",
    val temperature: Double = 0.0,
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val pressure: Int = 0,
    val weatherMain: String = "",
    val description: String,
    val iconCode: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
)