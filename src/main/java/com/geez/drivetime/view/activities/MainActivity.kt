package com.geez.drivetime.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geez.drivetime.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addTimeButton.setOnClickListener {
            val intent = Intent(this, NewTimeEntry::class.java)
            startActivity(intent)
        }

        historyButton.setOnClickListener {
            val intent = Intent(this, History::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            exitProcess(-1)
        }
    }*/
}