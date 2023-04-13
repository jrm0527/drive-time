package com.geez.drivetime.view.fragments

import android.app.Application
import android.icu.text.SimpleDateFormat
import androidx.compose.ui.platform.LocalContext
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
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

class DriveHistoryFragment : Fragment() {

    private var mViewModel: DriveTimeViewModel? = null

    private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val sdfTime = SimpleDateFormat("hh:mm aa", Locale.US)

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
        val allHistory by mViewModel!!.allDriveTime.observeAsState(listOf())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .border(BorderStroke(1.dp, Color.Red)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Header()
            if (allHistory.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    items(allHistory) { history ->
                        HistoryRow(history)
                    }
                }
            } else {
                Row(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
                ) {
                    Text(
                        text = "NO HISTORY AVAILABLE",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        style = TextStyle(
                            fontSize = 40.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        ),
                    )
                }
            }
            //Spacer(modifier = Modifier.weight(1f, false))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    /*.weight(1f, false)*/,
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
        Text(
            "HISTORY",
            modifier = Modifier
                .fillMaxWidth()
            /*.weight(.2f)*/,
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 45.sp
            )
        )
    }

    @Composable
    fun HistoryRow(history: DriveTimeClass) {
        Column(modifier = Modifier
            .border(BorderStroke(1.dp, Color.Red))
            .clickable {
                Toast
                    .makeText(requireContext(), "Clicked!", Toast.LENGTH_SHORT)
                    .show()
                val bundle = Bundle()
                bundle.putString("mode", "update")
                bundle.putParcelable("drive", history)
                findNavController().navigate(R.id.action_driveHistory_to_addUpdateDrive, bundle)
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(5.dp)
            ) {
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
                    text = "FINISH: " + if (history.driveFinish == null) {""} else {sdfTime.format(history.driveFinish)},
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

   @Composable
    fun Footer() {
       DefaultButtonStyle {
           Button(
               onClick = {findNavController().navigate(R.id.action_driveHistory_to_addUpdateDrive)},
               modifier = Modifier
                   .height(60.dp)
                   .width(80.dp),
           ) {
               Text(
                   text = "ADD HISTORY",
                   textAlign = TextAlign.Center,
                   style = TextStyle(
                       fontSize = 20.sp,
                   ),
               )
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
}