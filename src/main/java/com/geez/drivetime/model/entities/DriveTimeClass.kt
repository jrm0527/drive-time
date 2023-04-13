package com.geez.drivetime.model.entities

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "drive_time")
data class DriveTimeClass (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var date: Date,

    @ColumnInfo(name = "drive_start")
    var driveStart: Date,

    @ColumnInfo(name = "drive_finish")
    var driveFinish: Date?,

    var hours: Int?,
): Parcelable {

    companion object : Parceler<DriveTimeClass> {
        @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
        override fun DriveTimeClass.write(dest: Parcel, flags: Int) {
            dest.writeInt(this.id)
            dest.writeString(this.date.toString())
            dest.writeString(this.driveStart.toString())
            this.driveFinish?.time?.toString().let { dest.writeString(it) }
            this.hours?.let { dest.writeInt(it) }
        }

        @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
        override fun create(source: Parcel): DriveTimeClass {
            return create(source)
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
/*
class DriveTimeClass (
    val id: Int?, val date: String?, val driveStart: String?, val driveFinish: String?, val hours: String?)*/
