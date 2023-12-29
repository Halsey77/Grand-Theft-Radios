package com.halsey.grandtheftradios

import android.app.DownloadManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.halsey.grandtheftradios.RadioObjects.RadioDownloadManager
import com.halsey.grandtheftradios.RadioObjects.RadioPlayer
import com.halsey.grandtheftradios.RadioObjects.RadiosMap

class MainActivity : AppCompatActivity(), RadioPlayer.RadioPlayerCallback {
    private lateinit var radioDownloadManager: RadioDownloadManager
    private lateinit var radioPlayer: RadioPlayer
    private var gameName = ""
    private var stationName = ""

    private lateinit var gameSpinner: Spinner
    private lateinit var stationSpinner: Spinner
    private lateinit var gameText: TextView
    private lateinit var stationText: TextView
    private lateinit var downloadButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize variables
        gameSpinner = findViewById(R.id.gameSpinner)
        stationSpinner = findViewById(R.id.stationSpinner)
        gameText = findViewById(R.id.gameText)
        stationText = findViewById(R.id.stationText)
        downloadButton = findViewById(R.id.downloadButton)
        progressBar = findViewById(R.id.progressBar)
        playButton = findViewById(R.id.playButton)

        radioDownloadManager = RadioDownloadManager(this)
        radioPlayer = RadioPlayer(this, this.assets)

        initialize()
    }

    private fun onDownloadComplete(status: Int, url: String?) {
        val stationName = RadiosMap.instance?.getStationNameFromUrl(url)
        val text =
            if (status != DownloadManager.STATUS_SUCCESSFUL) "Failed to download radio station" else "$stationName has been added"

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        applyStateToDownloadButton()
        applyStateToPlayButton()
    }

    private fun initialize() {
        RadiosMap.instance //initialize the maps

        initGameSpinner()
        initStationSpinner()

        radioDownloadManager.addOnDownloadCompleteCallback { status, url ->
            onDownloadComplete(status, url)
        }

        downloadButton.setOnClickListener {
            downloadStation()
            applyStateToDownloadButton()
        }

        playButton.setOnClickListener {
            val radio = RadiosMap.instance?.getRadio(gameName, stationName)

            if (!radioDownloadManager.isStationDownloaded(radio?.url)) {
                Toast.makeText(this, "Please download the station first", Toast.LENGTH_LONG).show()
            } else {
                val mp3FilePath = radioDownloadManager.getAbsoluteFilePath(radio)
                radioPlayer.insertStationToPlayer(mp3FilePath)
            }
        }

        gameName = RadiosMap.GTA_GAMES[0]
        stationName = RadiosMap.instance?.getRadiosOfGame(gameName)?.get(0) ?: ""
    }

    private fun applyStateToPlayButton() {
        val radio = RadiosMap.instance?.getRadio(gameName, stationName)
        playButton.isEnabled = radioDownloadManager.isStationDownloaded(radio?.url)

        if (radio == null) return

        val mp3FilePath = radioDownloadManager.getAbsoluteFilePath(radio)
        if (radioPlayer.isRadioStationPlaying(mp3FilePath)) {
            playButton.text = getString(R.string.button_pause)
        } else {
            playButton.text = getString(R.string.button_play)
        }
    }

    private fun downloadStation() {
        val radio = RadiosMap.instance?.getRadio(gameName, stationName)
        radioDownloadManager.startDownload(radio)
    }

    private fun initStationSpinner() {
        stationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val name = stationSpinner.selectedItem.toString()
                stationText.text = name
                stationName = name
                applyStateToDownloadButton()
                applyStateToPlayButton()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //do nothing
            }
        }
        stationSpinner.setSelection(0)
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

        //make station spinner and gameText change when game spinner changes
        gameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                val name = gameSpinner.selectedItem.toString()
                setStationsSpinnerValues(name)
                gameText.text = name
                gameName = name
                applyStateToDownloadButton()
                applyStateToPlayButton()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //nothing happens
            }
        }
        gameSpinner.setSelection(0)
    }

    private fun setStationsSpinnerValues(gameName: String) {
        val stations = RadiosMap.instance?.getRadiosOfGame(gameName)
        if (stations == null) {
            Toast.makeText(this, "Failed to get stations of $gameName", Toast.LENGTH_LONG).show()
            return
        }

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
        val radio = RadiosMap.instance?.getRadio(gameName, stationName)

        if (radio == null) {
            downloadButton.text = getString(R.string.button_download)
            downloadButton.isEnabled = false
            hideProgressBar()
            return
        }

        val isBeingDownload = radioDownloadManager.isStationBeingDownloaded(radio.url)
        val hasBeenDownloaded = radioDownloadManager.isStationDownloaded(radio.url)

        if (isBeingDownload) {
            downloadButton.text = getString(R.string.button_is_downloading)
            downloadButton.isEnabled = false
            showProgressBar()
        } else if (hasBeenDownloaded) {
            downloadButton.text = getString(R.string.button_downloaded)
            downloadButton.isEnabled = false
            hideProgressBar()
        } else {
            downloadButton.text = getString(R.string.button_download)
            downloadButton.isEnabled = true
            hideProgressBar()
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        radioDownloadManager.onDestroy()
        radioPlayer.onDestroy()
    }

    override fun onRadioPlayerStateChanged(radioPlayerState: RadioPlayer.RadioPlayerState) {
        applyStateToPlayButton()
    }
}

//TODO: Update app icon
//TODO: Add a notification player for the radio player
//TODO: Update spinner to be more beautiful
//TODO: Add a button to delete the station
//TODO: Update the UI to be more beautiful