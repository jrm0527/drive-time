package com.geez.drivetime.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.geez.drivetime.model.entities.DriveTimeClass
import java.util.*

@Dao
interface DriveTimeDao {

    @Insert
    suspend fun insertDriveDetails(driveTime: DriveTimeClass)

    @Update
    suspend fun updateDriveTime(driveTime: DriveTimeClass)

    @Query("SELECT * FROM drive_time ORDER BY DATE")
    fun getAllDriveTime(): LiveData<List<DriveTimeClass>>

    @Query("SELECT * FROM drive_time WHERE NULLIF(drive_finish, '') IS NULL")
    fun getFinishDrive(): LiveData<List<DriveTimeClass>>

    @Query("DELETE FROM drive_time WHERE id = :id")
    fun deleteById(id: Int)

    /*@Query("SELECT * FROM drive_time WHERE NULLIF(drive_finish, '') IS NULL")
    fun getFinishDrive(): LiveData<DriveTimeClass>*/



/*    fun addDrive(drives: DriveTimeClass): Boolean {
        var result = false

        val searchResult = drives.date?.let { searchSameDate(it) }
        //val searchResult = searchSameDate(drives.date)
        val rowID = searchResult?.id
        val rowDriveFinish = searchResult?.driveFinish
        if (rowID != null && rowDriveFinish.isNullOrBlank()) {
            return result
        }

        var duration : String? = null

        if (!drives.driveFinish.isNullOrBlank()) {
            duration = addHours(
                DriveTimeClass(
                    null,
                    drives.date,
                    drives.driveStart,
                    drives.driveFinish,
                    null
                )
            ).toString()
        }

        val db = this.writableDatabase
        val values = ContentValues()
        if (!drives.date.isNullOrBlank()){ values.put(SqliteHelper.COLUMN_DATE, drives.date)}
        if (!drives.driveStart.isNullOrBlank()){ values.put(SqliteHelper.COLUMN_DRIVE_STARTED, drives.driveStart)}
        if (!drives.driveFinish.isNullOrBlank()){ values.put(SqliteHelper.COLUMN_DRIVE_FINISHED, drives.driveFinish)}
        if (duration != null) { values.put(SqliteHelper.COLUMN_HOURS, duration) }

        db.insert(SqliteHelper.TABLE_HISTORY, null, values)
        db.close()
        result = true
        return result
    }

    fun addDriveStart(drives: DriveTimeClass): Boolean {
        var result = false
        val searchResult = drives.date?.let { searchSameDate(it) }
        //val searchResult = searchSameDate(drives.date)
        val rowID = searchResult?.id
        val rowDriveFinish = searchResult?.driveFinish
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(SqliteHelper.COLUMN_DATE, drives.date)
        values.put(SqliteHelper.COLUMN_DRIVE_STARTED, drives.driveStart)

        if (rowID != null) {
            if (rowDriveFinish.isNullOrBlank()){
                db.close()
                return result
            }
        }
        db.insert(SqliteHelper.TABLE_HISTORY, null, values)
        db.close()
        result = true

        return result
    }

    fun addDriveFinish(drives: DriveTimeClass): Boolean {
        var result = false
        val searchResult = drives.date?.let { searchSameDate(it) }
        //val searchResult = searchSameDate(drives.date)
        val rowID = searchResult?.id
        val rowDate = searchResult?.date
        val rowDriveStart = searchResult?.driveStart
        val rowDriveFinish = searchResult?.driveFinish
        val rowHours = searchResult?.hours

        if (rowID != null && rowDriveFinish.isNullOrBlank()) {
            var db = this.writableDatabase

            val values = ContentValues()
            values.put(SqliteHelper.COLUMN_DATE, drives.date)
            values.put(SqliteHelper.COLUMN_DRIVE_FINISHED, drives.driveFinish)

            db.update(SqliteHelper.TABLE_HISTORY, values, "${SqliteHelper.COLUMN_ID} = $rowID", null)
            db.close()
            result = true
            addHours(
                DriveTimeClass(
                    rowID, rowDate.toString(), rowDriveStart,
                    drives.driveFinish, rowHours
                )
            )
            return result
        }
        return result
    }

    private fun addHours(drives: DriveTimeClass): String {
        val db = this.writableDatabase
        val rowID = drives.id
        val duration: String

        val parseFormat = SimpleDateFormat("HH:mm")
        val driveStart: Date = parseFormat.parse(drives.driveStart)
        val driveFinish: Date = parseFormat.parse(drives.driveFinish)

        val mills: Long = driveFinish.time - driveStart.time
        val minutes = mills.toDouble() / (1000 * 60)
        val totalTime = minutes / 60

        duration = String.format("%.2f", totalTime)

        if (rowID != null) {
            val values = ContentValues()
            values.put(SqliteHelper.COLUMN_HOURS, duration)
            db.update(SqliteHelper.TABLE_HISTORY, values, "${SqliteHelper.COLUMN_ID} = $rowID", null)
            db.close()
        }
        db.close()
        return duration
    }*/

}
