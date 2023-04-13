package com.geez.drivetime.view.fragments

import android.app.Application
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.compose.ui.platform.LocalContext
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.geez.drivetime.model.entities.DriveTimeClass
import com.geez.drivetime.ui.theme.AppTheme
import com.geez.drivetime.ui.theme.DefaultButtonStyle
import com.geez.drivetime.viewmodel.DriveTimeViewModel
import com.geez.drivetime.viewmodel.DriveTimeViewModelFactory
import java.util.*

class AddUpdateDriveFragment : Fragment() {

    private lateinit var _drive: DriveTimeClass
    private var date: Date? = null
    private var start: Date? = null
    private var finish: Date? = null

    private var mViewModel: DriveTimeViewModel? = null
    private var _mode: String? = null

    private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val sdfTime = SimpleDateFormat("hh:mm aa", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("mode")?.let { mode ->
            _mode = mode
        }
        when {
            Build.VERSION.SDK_INT >= 33 -> arguments?.getParcelable("drive", DriveTimeClass::class.java)?.let { drive ->
                _drive = drive
                date = drive.date
                start = drive.driveStart
            }
            else -> @Suppress("DEPRECATION") arguments?.getParcelable<DriveTimeClass>("drive")?.let { drive ->
                _drive = drive
                date = drive.date
                start = drive.driveStart
            }
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
                .border(BorderStroke(1.dp, Color.Red)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Header()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item() { DriveDetails() }
            }
            //Spacer(modifier = Modifier.weight(1f, false))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Footer()
            }
        }
    }

    @Composable
    fun Header() {
        //ADS
        Text(if (_mode == "update") {"Update Drive History"} else {"Add Drive History"},
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(textAlign = TextAlign.Center, fontSize = 45.sp)
        )
    }

    @Composable
    fun DriveDetails(){
        val mContext = LocalContext.current
        val mCalendar = Calendar.getInstance()

        val mYear = mCalendar.get(Calendar.YEAR)
        val mMonth = mCalendar.get(Calendar.MONTH)
        val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        val mDriveDate = remember { mutableStateOf("") }
        //if (_mode == "update") {mDriveDate.value = sdfDate.format(_drive.date)}

        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDriveDate.value = "$mYear-${String.format("%02d", mMonth+1)}-${String.format("%02d", mDayOfMonth)}"
                if (_mode == "update") {_drive.date = sdfDate.parse(mDriveDate.value) } else {date = sdfDate.parse(mDriveDate.value); Log.i("Date", date.toString())}
            }, mYear, mMonth, mDay
        )

        //Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "DATE: " + if (mDriveDate.value == "") { if (_mode == "update") {sdfDate.format(_drive.date)} else { "Select Date" } } else {mDriveDate.value},
                modifier = Modifier.clickable {mDatePickerDialog.show()},
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 30.sp)
            )
            Text(
                text = "HOURS: " + if (_mode == "update") {_drive.hours} else { "0" },
                //modifier = Modifier.weight(0.5f),
                textAlign = TextAlign.Center
            )
            /*Button(onClick = {
                mDatePickerDialog.show()
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
                Text(text = "Open Date Picker", color = Color.White)
            }*/

            // Adding a space of 100dp height
            Spacer(modifier = Modifier.size(100.dp))

            // Displaying the mDate value in the Text
            /*Text(text = "Selected Date: ${mDate.value}", fontSize = 30.sp, textAlign = TextAlign.Center)*/
        //}
    }

    @Composable
    private fun Footer() {
        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value) {
            AlertDialog(
                modifier = Modifier.padding(horizontal = 20.dp),
                onDismissRequest = {
                    openDialog.value = false
                },
                text = {
                    Text("Are you sure you want to delete ${_drive.id}?",
                        style = TextStyle(
                            fontSize = 18.sp,
                        ),
                    )
                },
                buttons = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DefaultButtonStyle {
                            Button(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(90.dp)
                                    .align(Alignment.CenterVertically),
                                onClick = { openDialog.value = false }
                            ) {
                                Text("No",
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                    ),
                                )
                            }
                        }
                        DefaultButtonStyle {
                            Button(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(90.dp)
                                    .align(Alignment.CenterVertically),
                                onClick = {
                                    mViewModel!!.delete(_drive.id)
                                    findNavController().popBackStack()
                                }
                            ) {
                                Text("Yes",
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                    ),
                                )
                            }
                        }
                    }
                }
            )
        }
        DefaultButtonStyle {
            Button(
                onClick = {
                    Log.i("Date", date.toString())
                    if (date == null) {
                        Toast.makeText(requireContext(), "You must select a date!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    /*if (start == null) {
                        Toast.makeText(requireContext(), "You must select a start time!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }*/
                    val drive = DriveTimeClass(date = date!!, driveStart = sdfTime.parse(sdfTime.format(date))!!, driveFinish = finish, hours = 0)
                    if (_mode == "update") {mViewModel!!.update(_drive)} else {mViewModel!!.insert(drive)}
                },
                modifier = Modifier
                    .height(60.dp)
                    .width(80.dp),
            ) {
                Text(
                    text = "SAVE",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                    ),
                )
            }
        }
        if (_mode == "update") {
            DefaultButtonStyle {
                Button(
                    onClick = { openDialog.value = true },
                    modifier = Modifier
                        .height(60.dp)
                        .width(80.dp),
                ) {
                    Text(
                        text = "DELETE",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 20.sp,
                        ),
                    )
                }
            }
        }
        DefaultButtonStyle {
            Button(
                onClick = {
                    findNavController().popBackStack()
                },
                modifier = Modifier
                    .height(60.dp)
                    .width(80.dp),
            ) {
                Text(
                    text = "BACK",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                    ),
                )
            }
        }
    }

    @Composable
    fun HistoryRow(history: DriveTimeClass) {
        Column(modifier = Modifier.border(BorderStroke(1.dp, Color.Red))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(5.dp)
            ) {
                /*val textState = remember { mutableStateOf(TextFieldValue()) }
                TextField(
                    value = textState.value,
                    onValueChange = { textState.value = it }
                )
                Text("The textfield has this text: " + textState.value.text)*/
                Text(
                    text = "DATE: ${sdfDate.format(history.date)}",
                    modifier = Modifier.weight(0.5f),
                    style = TextStyle(textAlign = TextAlign.Center)
                )
                Text(
                    text = "HOURS: ${history.hours}",
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.Center
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(5.dp)
            ) {
                Text(
                    text = "START: ${sdfTime.format(history.driveStart.time)}",
                    modifier = Modifier.weight(0.5f),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = "FINISH: ${sdfTime.format(history.driveFinish)}",
                    modifier = Modifier.weight(0.5f),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                )
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
    }
}