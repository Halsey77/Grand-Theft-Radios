package com.halsey.grandtheftradios

import android.app.DownloadManager
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.halsey.grandtheftradios.radio_objects.RadioDownloadManager
import com.halsey.grandtheftradios.radio_objects.RadioPlayerService
import com.halsey.grandtheftradios.radio_objects.RadiosMap
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var radioDownloadManager: RadioDownloadManager
    private lateinit var radioPlayerService: RadioPlayerService
    private var isRadioPlayerServiceBound = false
    private var gameName = ""
    private var stationName = ""
    private var radioServiceReceiver: BroadcastReceiver? = null
    private var filter: IntentFilter? = null

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

        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        //initialize variables
        gameSpinner = findViewById(R.id.gameSpinner)
        stationSpinner = findViewById(R.id.stationSpinner)
        gameText = findViewById(R.id.gameText)
        stationText = findViewById(R.id.stationText)
        downloadButton = findViewById(R.id.downloadButton)
        progressBar = findViewById(R.id.progressBar)
        playButton = findViewById(R.id.playButton)

        radioDownloadManager = RadioDownloadManager(this)

        setupBroadcastReceiver()
        startAndBindRadioPlayerService()
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(radioServiceReceiver!!, filter!!)
    }

    private fun setupBroadcastReceiver() {
        radioServiceReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.v("MainActivity", "onReceive {'$'}{intent?.action}")
                if (intent?.action == RadioPlayerService.RADIO_PLAYER_STATE_CHANGE) {
                    onRadioPlayerStateChanged()
                }
            }
        }
        filter = IntentFilter(RadioPlayerService.RADIO_PLAYER_STATE_CHANGE)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as RadioPlayerService.LocalBinder
            radioPlayerService = binder.getService()
            isRadioPlayerServiceBound = true

            initialize()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isRadioPlayerServiceBound = false
        }
    }

    private fun startAndBindRadioPlayerService() {
        val serviceIntent = Intent(this, RadioPlayerService::class.java)
        startForegroundService(serviceIntent)
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE)
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
        gameName = RadiosMap.GTA_GAMES[0]
        stationName = RadiosMap.instance?.getRadiosOfGame(gameName)?.get(0) ?: ""

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
                radioPlayerService.insertStationToPlayer(mp3FilePath, radio)
            }
        }
    }

    private fun applyStateToPlayButton() {
        val radio = RadiosMap.instance?.getRadio(gameName, stationName)
        playButton.isEnabled = radioDownloadManager.isStationDownloaded(radio?.url)

        if (radio == null) return

        val mp3FilePath = radioDownloadManager.getAbsoluteFilePath(radio)
        if (radioPlayerService.isRadioStationPlaying(mp3FilePath)) {
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
        radioDownloadManager.onDestroy()
        radioPlayerService.onDestroy()
        unregisterReceiver(radioServiceReceiver)
        if (isRadioPlayerServiceBound) unbindService(serviceConnection)
        super.onDestroy()
    }

    fun onRadioPlayerStateChanged() {
        applyStateToPlayButton()
    }
}

//TODO: Don't open app when play/pause button is pressed on notification
//TODO: Pause the radio when headphones are unplugged
//TODO: Update spinner to be more beautiful
//TODO: Add a button to delete the station
//TODO: Update the UI to be more beautiful