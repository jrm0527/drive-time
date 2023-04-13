package com.geez.drivetime.view.fragments

import android.app.Application
import android.widget.Toast
import android.icu.text.SimpleDateFormat
import androidx.compose.ui.platform.LocalContext
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.geez.drivetime.R
import com.geez.drivetime.model.entities.DriveTimeClass
import com.geez.drivetime.ui.theme.AppTheme
import com.geez.drivetime.ui.theme.DefaultButtonStyle
import com.geez.drivetime.viewmodel.DriveTimeViewModel
import com.geez.drivetime.viewmodel.DriveTimeViewModelFactory
import java.util.*

class NewDriveFragment : Fragment() {

    private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val sdfTime = SimpleDateFormat("HH:mm", Locale.US)

    private var _type: String? = null
    private var mViewModel: DriveTimeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("type")?.let { type ->
            _type = type
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply{
            setContent {
                AppTheme() {
                   Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background,
                    ) {
                       val owner = LocalViewModelStoreOwner.current
                        owner?.let {
                            mViewModel = viewModel(
                                it,
                                "DriveTimeViewModel",
                                DriveTimeViewModelFactory(LocalContext.current.applicationContext as Application)
                            )
                            ScreenSetup()
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun ScreenSetup() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            SetupHeader()
            SetupButtons()
        }
    }

    @Composable
    fun SetupHeader() {
        //ADS
        Text(
            "DRIVE SAFE TODAY!",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 34.sp
            )
        )
    }

    @Composable
    private fun SetupButtons(){
        val unfinishedDrive by mViewModel!!.finishDrive.observeAsState(listOf())
        val startDrive = remember { mutableStateOf(false) }
        if (startDrive.value) {
            if (unfinishedDrive.isNotEmpty()){
                Log.i("Drive", unfinishedDrive[0].id.toString())
                Toast.makeText(requireContext(), "You already have a drive in progress!", Toast.LENGTH_SHORT).show()
            } else {
                StartDrive()
            }
            startDrive.value = false
        }
        val finishDrive = remember { mutableStateOf(false) }
        if (finishDrive.value) {
            if (unfinishedDrive.isNotEmpty()) {
                FinishDrive(unfinishedDrive[0])
            } else {
                Toast.makeText(requireContext(), "You did not start drive!", Toast.LENGTH_SHORT).show()
            }
            finishDrive.value = false
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item() {
                DefaultButtonStyle() {
                    Button(
                        onClick = { startDrive.value = true },
                        modifier = Modifier
                            .padding(15.dp)
                            .height(100.dp)
                            .width(300.dp),
                    ) {
                        Text(
                            text = "START DRIVE",
                            textAlign = TextAlign.Center
                        )
                    }
                }
                DefaultButtonStyle() {
                    Button(
                        onClick = { finishDrive.value = true },
                        modifier = Modifier
                            .padding(15.dp)
                            .height(100.dp)
                            .width(300.dp),
                    ) {
                        Text(
                            text = "FINISH DRIVE",
                            textAlign = TextAlign.Center
                        )
                    }
                }
                DefaultButtonStyle() {
                    Button(
                        onClick = {
                            findNavController().popBackStack()
                        },
                        modifier = Modifier
                            .padding(15.dp)
                            .height(100.dp)
                            .width(300.dp),
                    ) {
                        Text(
                            text = "BACK",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun StartDrive() {
        //val drive = mViewModel?.finishDrive
        //val drive by mViewModel!!.finishDrive.observeAsState(DriveTimeClass(100, Date(2/1/2023),Date(2/1/2023), null, null))
        //val drive by mViewModel!!.finishDrive.observeAsState(listOf())
        /*if (drive.isNotEmpty()){
            Log.i("Drive", drive[0].id.toString())
            //allDriveTime.observeAsState(listOf())
            drive.let {

            }
        } else {*/
            val calendar = Calendar.getInstance()
            val dateTime = calendar.time
            val driveStart = sdfTime.format(dateTime)
            val date = sdfDate.format(dateTime)
            val driveDetails = DriveTimeClass(
                date = sdfDate.parse(date),
                driveStart = sdfTime.parse(driveStart),
                driveFinish = null,
                hours = null,
            )
            mViewModel!!.insert(driveDetails)
            Toast.makeText(requireContext(), "Drive safe!", Toast.LENGTH_SHORT).show()
        //}
    }

    @Composable
    private fun FinishDrive(drive: DriveTimeClass) {
        //val drive = mViewModel?.getDriveToFinish()
        //val drive = mViewModel!!.finishDrive.value
        //val drive by mViewModel!!.finishDrive.observeAsState(listOf())
        //if (drive.isNotEmpty()) {
            val calendar = Calendar.getInstance()
            val time = calendar.time
            val driveFinish = sdfTime.parse(sdfTime.format(time))
            val hours = addHours(drive, driveFinish)
            val driveDetails = DriveTimeClass(
                id = drive.id,
                date = drive.date,
                driveStart = drive.driveStart,
                driveFinish = driveFinish,
                hours = hours,
            )
            mViewModel!!.update(driveDetails)
            Toast.makeText(requireContext(), "You successfully saved your drive!", Toast.LENGTH_SHORT).show()
        /*} else {
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
        }*/
    }

    private fun addHours(drives: DriveTimeClass, finishTime: Date): Int {
        val mills: Long = finishTime.time - drives.driveStart.time
        val minutes = mills.toDouble() / (1000 * 60)

        return (minutes / 60).toInt()
    }

    /*private fun searchSameDate(dateTime: String): DriveTimeClass? {

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
    }*/
}