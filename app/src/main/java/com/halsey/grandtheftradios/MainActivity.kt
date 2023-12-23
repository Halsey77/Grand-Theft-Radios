package com.halsey.grandtheftradios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var gameSpinner: Spinner
    private lateinit var stationSpinner: Spinner
    private lateinit var gameText: TextView
    private lateinit var stationText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize variables
        gameSpinner = findViewById(R.id.gameSpinner)
        stationSpinner = findViewById(R.id.stationSpinner)
        gameText = findViewById(R.id.gameText)
        stationText = findViewById(R.id.stationText)

        //initialize the activity
        initialize()
    }

    private fun initialize() {
        setGameSpinner()
    }

    private fun setGameSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.gta_games,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            gameSpinner.adapter = adapter
        }

        gameSpinner.setSelection(0)

        //make station spinner change when game spinner changes
        gameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                //set station spinner to the correct stations
                setStationSpinner(position);
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //nothing happens
            }
        }
    }

    private fun setStationSpinner(position: Int) {
        val stations = resources.getStringArray(R.array.gta_stations)[position].split(';').toTypedArray();

        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            stations
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            stationSpinner.adapter = adapter
        }
    }
}