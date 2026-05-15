
package basedatos.movil.meteoreport.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_alerts")
data class WeatherAlert(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val event: String,
    val severity: String,
    val startTime: Long,
    val endTime: Long,
    val reportId: Int
)