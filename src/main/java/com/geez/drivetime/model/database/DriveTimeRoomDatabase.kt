package com.geez.drivetime.model.database

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.geez.drivetime.model.entities.Converters
import com.geez.drivetime.model.entities.DriveTimeClass

@Database(entities = [DriveTimeClass::class], version = 1)
@TypeConverters(Converters::class)
abstract class DriveTimeRoomDatabase : RoomDatabase() {

    abstract fun driveTimeDao(): DriveTimeDao

    companion object{

        private var INSTANCE: DriveTimeRoomDatabase? = null

        private val MIGRATION_2_1 = object : Migration(2, 1) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.i("Database", "2_1 completed")
            }
        }

        fun getDatabase(context: Context): DriveTimeRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DriveTimeRoomDatabase::class.java,
                        "drive_time.db"
                    )
                        //.createFromAsset("database/collectibles.db")
                        .allowMainThreadQueries()
                        //.fallbackToDestructiveMigration()
                        .addMigrations(MIGRATION_2_1)
                        .build()
                    INSTANCE = instance

                    /*instance = RoomAssetHelper.databaseBuilder(
                        context.applicationContext,
                        DriveTimeRoomDatabase::class.java,
                        "drive_time.db",
                        "database/",
                        1,
                        arrayOf(
                            TablePreserve("collectibles",
                            preserveColumns = arrayOf("collected"),
                            matchByColumns = arrayOf("id"))
                        ))
                        //.addMigrations(MIGRATION_2_1)
                        .build()
                    INSTANCE = instance*/


                }
                return instance
            }
        }
    }
}