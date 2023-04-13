
package com.geez.drivetime
/*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.geez.drivetime.model.entities.DriveTimeClass
import com.opencsv.CSVReader
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception

class Settings : AppCompatActivity() {

    lateinit var dbHandler: SqliteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        dbHandler = SqliteHelper(this, null)

        setSupportActionBar(toolbar_settings)

        val actionbar = supportActionBar
        if(actionbar !=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "SETTINGS"
        }
        toolbar_settings.setNavigationOnClickListener {
            onBackPressed()
        }

        btnClearDatabase.setOnClickListener {
            val dialog = AlertDialog.Builder(this@Settings)
            dialog.setMessage("Your information will be lost forever! Are you sure you want to clear your database?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    clearDatabase()
                    Toast.makeText(
                        this,
                        "Database cleared successfully",
                        Toast.LENGTH_SHORT)
                        .show()
                }
                .setNegativeButton("No") {dialog, id ->
                    dialog.dismiss()
                }
            val alert = dialog.create()
            alert.show()
        }

        btnBackupDatabase.setOnClickListener {
            //backup all records
            exportCSV()
        }

        btnRestoreDatabase.setOnClickListener {
            //restore all records
            importCSV()
        }
    }

    private fun clearDatabase(){
        val dbHandler = SqliteHelper(this, null)
        dbHandler.deleteDatabase()
    }

    private fun exportCSV() {
        val folder = getExternalFilesDir("DriveTimeBackup")

        //file name
        val csvFileName = "DriveTime_Backup.csv"

        //file name and path
        val fileNameAndPath = "$folder/$csvFileName"

        //get records to save in backup
        var recordList = ArrayList<DriveTimeClass>()
        recordList.clear()
        recordList = dbHandler.getAllDatesList()

        try {
            val fw = FileWriter(fileNameAndPath)
            for (i in recordList.indices){
                fw.append(""+ recordList[i].id) //id
                fw.append(",")
                fw.append(""+ recordList[i].date) //date
                fw.append(",")
                fw.append(""+ recordList[i].driveStart) //drive start
                fw.append(",")
                fw.append(""+ recordList[i].driveFinish) //drive finish
                fw.append(",")
                fw.append(""+ recordList[i].hours) //hours
                fw.append("\n")
            }
            fw.flush()
            fw.close()
            Toast.makeText(this, "Backed up saved to $fileNameAndPath!", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun importCSV() {
        val filePathAndName =
            getExternalFilesDir("DriveTimeBackup/DriveTime_Backup.csv").toString()

        val csvFile = File(filePathAndName)

        if (csvFile.exists()) {
            //exists
            try {
                val csvReader = CSVReader(FileReader(csvFile.absolutePath))
                var nextLine: Array<String>
                while (csvReader.readNext().also { nextLine = it } != null) {
                    //get records from csv
                    val id = nextLine[0]
                    val date = nextLine[1]
                    val driveStart = nextLine[2]
                    val driveFinish = nextLine[3]
                    val hours = nextLine[4]

                    //add to db
                    dbHandler.restoreTimes(
                        DriveTimeClass(
                            id.toInt(),
                            date,
                            driveStart,
                            driveFinish,
                            hours
                        )
                    )
                    Toast.makeText(this, "Database Restored", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }else{
            //does not exist
            Toast.makeText(this, "Backup not found...", Toast.LENGTH_SHORT).show()
        }
    }
}*/
