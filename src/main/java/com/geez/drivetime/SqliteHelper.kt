
package com.geez.drivetime
/*
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.geez.drivetime.model.entities.DriveTimeClass
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SqliteHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1 // Version of the database
        private const val DATABASE_NAME = "TrackInTime.db" // Name of the database
        private const val TABLE_HISTORY = "history" // Table Name
        private const val COLUMN_ID = "_id" // Column ID
        private const val COLUMN_DATE = "date" // Column for date
        private const val COLUMN_DRIVE_STARTED = "drive_started" // Column for drive started
        private const val COLUMN_DRIVE_FINISHED = "drive_finished" // Column for Drive finished
        private const val COLUMN_HOURS = "hours" // Column for time worked
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // CREATE TABLE history (_id INTEGER PRIMARY KEY, column_date TEXT)
        val CREATE_TIME_TABLE = ("CREATE TABLE "
                + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_DATE + " TEXT," + COLUMN_DRIVE_STARTED + " TEXT," + COLUMN_DRIVE_FINISHED
                + " TEXT," + COLUMN_HOURS + " TEXT)")
        db?.execSQL(CREATE_TIME_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        */
/*if (newVersion > oldVersion){
            val upgradeTable = ("ALTER TABLE $TABLE_HISTORY ADD COLUMN $COLUMN_HOURS TEXT")
            db.execSQL(upgradeTable)
        }*//*

    }

    fun addDrive(drives: DriveTimeClass): Boolean {
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
        if (!drives.date.isNullOrBlank()){ values.put(COLUMN_DATE, drives.date)}
        if (!drives.driveStart.isNullOrBlank()){ values.put(COLUMN_DRIVE_STARTED, drives.driveStart)}
        if (!drives.driveFinish.isNullOrBlank()){ values.put(COLUMN_DRIVE_FINISHED, drives.driveFinish)}
        if (duration != null) { values.put(COLUMN_HOURS, duration) }

        db.insert(TABLE_HISTORY, null, values)
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
        values.put(COLUMN_DATE, drives.date)
        values.put(COLUMN_DRIVE_STARTED, drives.driveStart)

        if (rowID != null) {
            if (rowDriveFinish.isNullOrBlank()){
                db.close()
                return result
            }
        }
        db.insert(TABLE_HISTORY, null, values)
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
            values.put(COLUMN_DATE, drives.date)
            values.put(COLUMN_DRIVE_FINISHED, drives.driveFinish)

            db.update(TABLE_HISTORY, values, "$COLUMN_ID = $rowID", null)
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
            values.put(COLUMN_HOURS, duration)
            db.update(TABLE_HISTORY, values, "$COLUMN_ID = $rowID", null)
            db.close()
        }
        db.close()
        return duration
    }

    fun deleteDatabase() {
        val db = this.writableDatabase
        val clearDB = ("DELETE FROM $TABLE_HISTORY")
        db.execSQL(clearDB)
        db.close()
    }

    fun deleteRecord(drives: DriveTimeClass) : Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_HISTORY, COLUMN_ID + "=" + drives.id, null)
        db.close()
        return success
    }

    fun getAllDatesList() : ArrayList<DriveTimeClass> {
        val list: ArrayList<DriveTimeClass> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_HISTORY"
        val db = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = db.query(TABLE_HISTORY, null, null, null, null, null,
                "$COLUMN_DATE DESC, $COLUMN_DRIVE_STARTED DESC"
            )
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var date: String?
        var driveStart: String?
        var driveFinish: String?
        var hours: String?
        if (cursor.moveToFirst()) {
            do {
                id = (cursor.getInt(cursor.getColumnIndex(COLUMN_ID)))
                date = (cursor.getString(cursor.getColumnIndex(COLUMN_DATE)))
                driveStart = cursor.getString(cursor.getColumnIndex(COLUMN_DRIVE_STARTED))
                driveFinish = cursor.getString(cursor.getColumnIndex(COLUMN_DRIVE_FINISHED))
                hours = cursor.getString(cursor.getColumnIndex(COLUMN_HOURS))
                if (date.isNullOrEmpty()){
                    date = "invalid date"
                }

                val times = DriveTimeClass(id, date, driveStart, driveFinish, hours)
                list.add(times)

            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun getTotalHours() : String {
        var hours: Double
        val db = this.readableDatabase

        val cursor = db.rawQuery(
            "SELECT SUM($COLUMN_HOURS) AS Total_Hours FROM $TABLE_HISTORY",
            null
        );

        if (cursor.moveToFirst()) {
            hours = cursor.getDouble(cursor.getColumnIndex("Total_Hours"));// get final total

            cursor.close()
            db.close()
            return String.format("%.2f", hours)
        }
        cursor.close()
        db.close()
        return "0.0"
    }

    private fun searchSameDate(dateTime: String): DriveTimeClass? {

        val query = "SELECT * from $TABLE_HISTORY WHERE $COLUMN_DATE = \"$dateTime\""
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            var id: Int
            var date: String?
            var driveStart: String?
            var driveFinish: String?
            var hours: String?
            if (cursor.moveToFirst()) {
                do {
                    id = (cursor.getInt(cursor.getColumnIndex(COLUMN_ID)))
                    date = (cursor.getString(cursor.getColumnIndex(COLUMN_DATE)))
                    driveStart = cursor.getString(cursor.getColumnIndex(COLUMN_DRIVE_STARTED))
                    driveFinish = cursor.getString(cursor.getColumnIndex(COLUMN_DRIVE_FINISHED))
                    hours = cursor.getString(cursor.getColumnIndex(COLUMN_HOURS))

                    if (driveFinish.isNullOrBlank()) {
                        cursor.close()
                        db.close()
                        return DriveTimeClass(id, date, driveStart, driveFinish, hours)
                    }
                } while (cursor.moveToNext())
                cursor.close()
                db.close()
                return null
            }
        }
        return null
    }

    fun updateTimes(drives: DriveTimeClass): Boolean{
        //fun updateTimes(id:String, clockIn:String, lunchOut:String, lunchIn:String,  clockOut:String) {
        var result = false
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_DATE, drives.date)
        if (drives.driveStart != null){values.put(COLUMN_DRIVE_STARTED, drives.driveStart)}
        if (drives.driveFinish != null)values.put(COLUMN_DRIVE_FINISHED, drives.driveFinish)

        // Updating Row
        db.update(TABLE_HISTORY, values, "$COLUMN_ID= " + drives.id, null)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        result = true
        if (!drives.driveFinish.isNullOrBlank()) {
            addHours(
                DriveTimeClass(
                    drives.id, drives.date, drives.driveStart, drives.driveFinish,null)
            )
        }
        return result
    }

    fun restoreTimes(drives: DriveTimeClass): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, drives.id)
        values.put(COLUMN_DATE, drives.date)
        values.put(COLUMN_DRIVE_STARTED, drives.driveStart)
        values.put(COLUMN_DRIVE_FINISHED, drives.driveFinish)
        values.put(COLUMN_HOURS, drives.hours) //added in 0.5.4

        // Updating Row
        db.insert(TABLE_HISTORY, null, values)

        db.close() // Closing database connection
        return true
    }
}*/
