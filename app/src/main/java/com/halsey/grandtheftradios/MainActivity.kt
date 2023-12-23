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
        initGameSpinner()
        initStationSpinner()
    }

    private fun initStationSpinner() {
        stationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val stationName = stationSpinner.selectedItem.toString()
                stationText.text = stationName
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //do nothing
            }

        }
    }

    private fun initGameSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.gta_games,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            gameSpinner.adapter = adapter
        }

        gameSpinner.setSelection(0)

        //make station spinner and gameText change when game spinner changes
        gameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                setStationValues(position)
                gameText.text = resources.getStringArray(R.array.gta_games)[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //nothing happens
            }
        }
    }

    private fun setStationValues(position: Int) {
        val stations = resources.getStringArray(R.array.gta_stations)[position].split(';').toTypedArray()

        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            stations
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            stationSpinner.adapter = adapter
        }

        stationSpinner.setSelection(0)
    }
}