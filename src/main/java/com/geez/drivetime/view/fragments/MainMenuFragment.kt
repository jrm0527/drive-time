package com.geez.drivetime.view.fragments

import android.app.Application
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.geez.drivetime.ui.theme.AppTheme
import com.geez.drivetime.R
import com.geez.drivetime.ui.theme.DefaultButtonStyle
import com.geez.drivetime.viewmodel.DriveTimeViewModel
import com.geez.drivetime.viewmodel.DriveTimeViewModelFactory
import java.util.*

/*import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView*/

class MainMenuFragment : Fragment() {

    private var _menu: String? = null
    private var mViewModel: DriveTimeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("menu")?.let { menu ->
            _menu = menu
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme() {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        val owner = LocalViewModelStoreOwner.current

                        owner?.let {
                            mViewModel = viewModel(
                                it,
                                "DriveTimeViewModel",
                                DriveTimeViewModelFactory(LocalContext.current.applicationContext as Application)
                            )
                            SetupScreen()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SetupScreen(){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SetupHeader()
            Spacer(modifier = Modifier.padding(10.dp))
            SetupButtons()
        }
    }

    @Composable
    private fun SetupHeader(){
        //AdvertView()
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Let's Drive!",
            style = TextStyle(
                fontSize = 34.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(20.dp)
        )
    }

    @Composable
    private fun SetupButtons(){
        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value) {
            AlertDialog(
                modifier = Modifier.padding(horizontal = 20.dp),
                onDismissRequest = {
                    openDialog.value = false
                },
                text = {
                    Text("Are you sure you want to exit?",
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
                                    activity?.finish()
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item() {
                DefaultButtonStyle() {
                    Button(
                        onClick = { findNavController().navigate(R.id.action_mainMenu_to_newDrive) },
                        modifier = Modifier
                            .padding(15.dp)
                            .height(100.dp)
                            .width(300.dp),
                    ) {
                        Text(
                            text = "NEW DRIVE",
                            textAlign = TextAlign.Center
                        )
                    }
                }
                DefaultButtonStyle() {
                    Button(
                        onClick = { findNavController().navigate(R.id.action_mainMenu_to_driveHistory) },
                        modifier = Modifier
                            .padding(15.dp)
                            .height(100.dp)
                            .width(300.dp),
                    ) {
                        Text(
                            text = "HISTORY",
                            textAlign = TextAlign.Center
                        )
                    }
                }
                DefaultButtonStyle() {
                    Button(
                        onClick = {
                            /*openDialog.value = true*/
                        },
                        modifier = Modifier
                            .padding(15.dp)
                            .height(100.dp)
                            .width(300.dp),
                    ) {
                        Text(
                            text = "SETTINGS",
                            textAlign = TextAlign.Center
                        )
                    }
                }
                DefaultButtonStyle() {
                    Button(
                        onClick = {
                            openDialog.value = true
                        },
                        modifier = Modifier
                            .padding(15.dp)
                            .height(100.dp)
                            .width(300.dp),
                    ) {
                        Text(
                            text = "Exit",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
/*

@Composable
private fun SetupCollectibles() {
    Column(
        modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item() {
                val allCollectibles by mViewModel!!.allCollectibles.observeAsState(listOf())
                val allCollected by mViewModel!!.getAllCollected().observeAsState(listOf())
                if (allCollectibles.isNotEmpty()) {
                    DefaultButtonStyle {
                        Button(
                            onClick = {
                                val bundle = Bundle()
                                bundle.putString("type", "All")
                                findNavController().navigate(R.id.action_menu_to_collectiblesList, bundle)
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .height(130.dp)
                                .width(300.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append("All Collectibles")
                                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                                        append("\n\nCollected ${allCollected.size} / ${allCollectibles.size}")
                                    }
                                },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "LOADING",
                            style = TextStyle(
                                fontSize = 40.sp,
                                color = Color.White
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
            items(collectibleList()) { type ->
                val allCollectibleType by mViewModel!!.getCollectibleType(type).observeAsState(listOf())
                val collectedType by mViewModel!!.getCollectedByType(type).observeAsState(listOf())
                if (allCollectibleType.isNotEmpty()) {
                    DefaultButtonStyle {
                        Button(
                            onClick = {
                                val bundle = Bundle()
                                bundle.putString("type", type)
                                findNavController().navigate(R.id.action_menu_to_collectiblesList, bundle)
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .height(130.dp)
                                .width(300.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append(
                                        when (type) {
                                            "Workbench" -> "Workbenches"
                                            else -> "${type}s"
                                        }
                                    )
                                    withStyle(style = SpanStyle(fontSize = 17.sp)) {
                                        append("\n\nCollected ${collectedType.size} / ${allCollectibleType.size}")
                                    }
                                },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "LOADING",
                            style = TextStyle(
                                fontSize = 40.sp,
                                color = Color.White
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
        SetupFooter()
    }
}

@Composable
private fun SetupChapterButtons() {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(chapterList()) { chapter ->
                DefaultButtonStyle {
                    val collected by mViewModel!!.getChapterCollected(chapter).observeAsState(listOf())
                    val collectibles by mViewModel!!.getChapterCollectibles(chapter).observeAsState(listOf())
                    Button(
                        onClick = {
                            val bundle = Bundle()
                            bundle.putString("chapter", chapter)
                            findNavController().navigate(R.id.action_menu_to_chapterSelect, bundle)
                        },
                        modifier = Modifier
                            .padding(15.dp)
                            .height(100.dp)
                            .width(300.dp)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append(chapter)
                                withStyle(style = SpanStyle(fontSize = 18.sp)) {
                                    append("\n\nCollected ${collected.size} / ${collectibles.size}")
                                }
                            },
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        SetupFooter()
    }
}*/

/*@Composable
private fun SetupFooter(){
    Spacer(modifier = Modifier.padding(5.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    )
    {
        DefaultButtonStyle {
            Button(
                onClick = {findNavController().navigate(R.id.action_menu_self)},
                modifier = Modifier
                    .height(60.dp)
                    .width(80.dp)
                    .align(Alignment.CenterVertically),
            ) {
                Text(
                    text = "Back",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                    ),
                )
            }
        }
    }
}*/

/*@Composable
private fun AdvertView(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = if (BuildConfig.DEBUG){
                    context.getString(R.string.ad_id_banner_test)
                } else {
                    context.getString(R.string.ad_id_banner_guideView)
                }
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}*/
}