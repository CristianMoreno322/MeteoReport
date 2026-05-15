package basedatos.movil.meteoreport.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import basedatos.movil.meteoreport.model.WeatherAlert
import basedatos.movil.meteoreport.model.WeatherReport

@Database(entities = [WeatherReport::class, WeatherAlert::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(context, AppDatabase::class.java, "meteoreport.db")
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
        }
    }
}