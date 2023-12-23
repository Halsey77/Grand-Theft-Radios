package com.halsey.grandtheftradios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        initialize()
    }

    private fun initialize() {
        setGameSpinner()
    }

    private fun setGameSpinner() {
        gameSpinner = findViewById(R.id.gameSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.gta_games,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            gameSpinner.adapter = adapter
        }
    }
}