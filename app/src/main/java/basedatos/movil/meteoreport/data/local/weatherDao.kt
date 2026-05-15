package basedatos.movil.meteoreport.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import basedatos.movil.meteoreport.model.WeatherReport

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_reports ORDER BY timestamp DESC")
    fun getAllReports(): LiveData<List<WeatherReport>>

    @Query("SELECT * FROM weather_reports WHERE city = :city LIMIT 1")
    suspend fun getReportByCity(city: String): WeatherReport?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: WeatherReport)

    @Query("SELECT * FROM weather_reports WHERE isSynced = 0")
    suspend fun getPendingSync(): List<WeatherReport>
}