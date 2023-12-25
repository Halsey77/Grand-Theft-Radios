package com.halsey.grandtheftradios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.halsey.grandtheftradios.RadioObjects.RadioDownloadManager
import com.halsey.grandtheftradios.RadioObjects.RadiosMap

class MainActivity : AppCompatActivity() {
    private lateinit var radioDownloadManager: RadioDownloadManager

    private lateinit var gameSpinner: Spinner
    private lateinit var stationSpinner: Spinner
    private lateinit var gameText: TextView
    private lateinit var stationText: TextView
    private lateinit var downloadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize variables
        gameSpinner = findViewById(R.id.gameSpinner)
        stationSpinner = findViewById(R.id.stationSpinner)
        gameText = findViewById(R.id.gameText)
        stationText = findViewById(R.id.stationText)
        downloadButton = findViewById(R.id.downloadButton)

        radioDownloadManager = RadioDownloadManager(this)
        radioDownloadManager.addOnDownloadCompleteCallback { url ->
            onDownloadComplete(url)
        }

        //initialize the activity
        initialize()
    }

    private fun onDownloadComplete(url: String) {
        val stationName = RadiosMap.getInstance().getStationNameFromUrl(url)
        Toast.makeText(this, "$stationName has been added", Toast.LENGTH_SHORT).show()
        applyStateToDownloadButton()
    }

    private fun initialize() {
        RadiosMap.getInstance() //initialize the maps

        initGameSpinner()
        initStationSpinner()

        downloadButton.setOnClickListener {
            downloadStation()
            applyStateToDownloadButton()
        }
    }

    private fun downloadStation() {
        val gameName = gameSpinner.selectedItem.toString()
        val stationName = stationSpinner.selectedItem.toString()
        val radio = RadiosMap.getInstance().getRadio(gameName, stationName)

        radioDownloadManager.startDownload(radio)
    }

    private fun initStationSpinner() {
        stationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val stationName = stationSpinner.selectedItem.toString()
                stationText.text = stationName
                applyStateToDownloadButton()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //do nothing
            }
        }
    }

    private fun initGameSpinner() {
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            RadiosMap.GTA_GAMES,
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

                val gameName = gameSpinner.selectedItem.toString()
                setStationsSpinnerValues(gameName)
                gameText.text = gameName
                applyStateToDownloadButton()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //nothing happens
            }
        }
    }

    private fun setStationsSpinnerValues(gameName: String) {
        val stations = RadiosMap.getInstance().getRadiosOfGame(gameName)

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

    private fun applyStateToDownloadButton() {
        val gameName = gameSpinner.selectedItem.toString()
        val stationName = stationSpinner.selectedItem.toString()
        val radio = RadiosMap.getInstance().getRadio(gameName, stationName)

        val isBeingDownload = radioDownloadManager.isStationBeingDownloaded(radio.url)
        val hasBeenDownloaded = radioDownloadManager.isStationDownloaded(radio.url)

        if(isBeingDownload) {
            downloadButton.text = getString(R.string.button_is_downloading)
            downloadButton.isEnabled = false
        } else if(hasBeenDownloaded) {
            downloadButton.text = getString(R.string.button_downloaded)
            downloadButton.isEnabled = false
        } else {
            downloadButton.text = getString(R.string.button_download)
            downloadButton.isEnabled = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        radioDownloadManager.onDestroy()
    }
}