package bme.hit.greenhouse

import android.app.Application
import androidx.room.Room
import bme.hit.greenhouse.data.SectorDatabase
import bme.hit.greenhouse.data.repository.SectorRepositoryImpl
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class GreenHouseApplication : Application() {

    companion object {
        private lateinit var db: SectorDatabase

        lateinit var repository: SectorRepositoryImpl
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            SectorDatabase::class.java,
            "sector_database"
        ).fallbackToDestructiveMigration().build()

        repository = SectorRepositoryImpl(db.dao)


    }
}