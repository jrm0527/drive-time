package com.geez.drivetime.model.database

import android.content.Context
import android.content.SharedPreferences
import com.geez.drivetime.view.activities.CLOCK_TYPE
import com.geez.drivetime.view.activities.PREFERENCE_NAME

class PrefRepository(val context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    private val editor = pref.edit()

    private fun Int.put(value: Int){
        editor.putInt(this.toString(), value)
        editor.commit()
    }

    private fun Int.getInt() = pref.getInt(this.toString(), 0)

    fun setClockType(value: Int){
        CLOCK_TYPE.put(value)
    }

    fun getClockType() = CLOCK_TYPE.getInt()
}