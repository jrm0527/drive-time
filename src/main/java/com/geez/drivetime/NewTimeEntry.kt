
package com.geez.drivetime
/*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geez.drivetime.model.entities.DriveTimeClass
import kotlinx.android.synthetic.main.activity_new_time_entry.*
import java.text.SimpleDateFormat
import java.util.*

class NewTimeEntry : AppCompatActivity() {

    private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val sdfTime = SimpleDateFormat("HH:mm", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_time_entry)

        setSupportActionBar(toolbar_new_entry)

        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "NEW DRIVE ENTRY"
        }
        toolbar_new_entry.setNavigationOnClickListener {
            onBackPressed()
        }

        btnDriveStart.setOnClickListener {
            addDriveStartToDatabase()
        }

        btnDriveFinish.setOnClickListener {
            addDriveFinishToDatabase()
        }
    }

    private fun addDriveStartToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        val driveStart = sdfTime.format(dateTime).toString()
        val date = sdfDate.format(dateTime)

        val dbHandler = SqliteHelper(this, null)
        val success = dbHandler.addDriveStart(DriveTimeClass(null, date, driveStart, null, null))
        if (success){
            Toast.makeText(
                this,
                "Drive Started Successful",
                Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                this,
                "Another drive time was not finished!",
                Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun addDriveFinishToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        val driveFinish = sdfTime.format(dateTime).toString()
        val date = sdfDate.format(dateTime).toString()

        val dbHandler = SqliteHelper(this, null)
        val success = dbHandler.addDriveFinish(DriveTimeClass(null, date, null, driveFinish, null))
        if (success){
            Toast.makeText(
                this,
                "Finish drive was successful",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            Toast.makeText(
                this,
                "ERROR",
                Toast.LENGTH_SHORT)
                .show()
        }
    }
}*/
