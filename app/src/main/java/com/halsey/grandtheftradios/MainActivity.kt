package com.halsey.grandtheftradios

import android.app.DownloadManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.halsey.grandtheftradios.RadioObjects.RadioDownloadManager
import com.halsey.grandtheftradios.RadioObjects.RadiosMap

class MainActivity : AppCompatActivity() {
    private lateinit var radioDownloadManager: RadioDownloadManager
    private var mediaPlayer: MediaPlayer? = null
    private var isStationPlaying = false
    private var isStationPrepared = false

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

        initialize()
    }

    private fun onDownloadComplete(status: Int, url: String?) {
        val stationName = RadiosMap.getInstance().getStationNameFromUrl(url)
        val text = if (status != DownloadManager.STATUS_SUCCESSFUL) "Failed to download radio station" else "$stationName has been added"

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        applyStateToDownloadButton()
        applyStateToPlayButton()
    }

    private fun initialize() {
        RadiosMap.getInstance() //initialize the maps

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
            if(isStationPlaying) {
                pauseStation()
            }
            else {
                playStation()
            }
        }
    }

    private fun applyStateToPlayButton() {
        val gameName = gameSpinner.selectedItem.toString()
        val stationName = stationSpinner.selectedItem.toString()
        val radio = RadiosMap.getInstance().getRadio(gameName, stationName)

        val hasBeenDownloaded = radioDownloadManager.isStationDownloaded(radio.url)
        playButton.isEnabled = hasBeenDownloaded

        playButton.text = if (isStationPlaying) getString(R.string.button_pause) else getString(R.string.button_play)
    }

    private fun pauseStation() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause()
                isStationPlaying = false
                applyStateToPlayButton()
            }
        }
    }

    private fun playStation() {
        val gameName = gameSpinner.selectedItem.toString()
        val stationName = stationSpinner.selectedItem.toString()
        val radio = RadiosMap.getInstance().getRadio(gameName, stationName)

        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }

        mediaPlayer?.apply {
            if(isPlaying) {
                return
            }

            if(isStationPrepared) { //if the station has already been prepared, just play it
                start()
                isStationPlaying = true
                applyStateToPlayButton()
            }
            else {  //otherwise, prepare it and then play it
                try {
                    val mp3FilePath = radioDownloadManager.getAbsoluteFilePath(radio)
                    setDataSource(mp3FilePath)
                    prepareAsync()

                    setOnPreparedListener {
                        start()
                        isStationPlaying = true
                        isStationPrepared = true
                        applyStateToPlayButton()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Failed to play radio station", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }
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
                resetStationPlayerStates()
                applyStateToPlayButton()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //do nothing
            }
        }
    }

    private fun resetStationPlayerStates() {
            mediaPlayer?.apply {
                stop()
                reset()
                release()
                mediaPlayer = null
                isStationPlaying = false
                isStationPrepared = false
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
                resetStationPlayerStates()
                applyStateToPlayButton()
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
            showProgressBar()
        } else if(hasBeenDownloaded) {
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
        mediaPlayer?.release()
    }
}