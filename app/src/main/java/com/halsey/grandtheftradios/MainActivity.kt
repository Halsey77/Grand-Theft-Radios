package com.halsey.grandtheftradios

import android.app.DownloadManager
import android.content.*
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.halsey.grandtheftradios.custom.CarouselAdapter
import com.halsey.grandtheftradios.notification.RequestPermission
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
    private var broadcastReceiver: BroadcastReceiver? = null
    private var filter: IntentFilter? = null

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
        stationSpinner = findViewById(R.id.stationSpinner)
        gameText = findViewById(R.id.gameText)
        stationText = findViewById(R.id.stationText)
        downloadButton = findViewById(R.id.downloadButton)
        progressBar = findViewById(R.id.progressBar)
        playButton = findViewById(R.id.playButton)

        radioDownloadManager = RadioDownloadManager(this)

        setupBroadcastReceiver()
//        startAndBindRadioPlayerService()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestPermission.requestNotificationPermission(this) {
                startAndBindRadioPlayerService()
            }
        } else {
            startAndBindRadioPlayerService()
        }
    }

    override fun onResume() {
        super.onResume()
//        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver!!, filter!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReceiver!!, filter!!, RECEIVER_EXPORTED)
        } else {
            registerReceiver(broadcastReceiver!!, filter!!)
        }
    }

    private fun setupBroadcastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    RadioPlayerService.RADIO_PLAYER_STATE_CHANGE -> onRadioPlayerStateChanged()
                    AudioManager.ACTION_AUDIO_BECOMING_NOISY -> {
                        Log.e("MainActivity", "Headphones unplugged")
                        radioPlayerService.onAudioBecomingNoisy()
                        applyStateToPlayButton()
                    }
                }

            }
        }
        filter = IntentFilter(RadioPlayerService.RADIO_PLAYER_STATE_CHANGE)
        filter?.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
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

        setupGameCarousel()
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

    private fun setupGameCarousel() {
        val recyclerView = findViewById<RecyclerView>(R.id.game_recycler_view)

        val layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
        layoutManager.carouselAlignment = CarouselLayoutManager.ALIGNMENT_CENTER
        recyclerView.layoutManager = layoutManager

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        val adapter = CarouselAdapter(this@MainActivity, RadiosMap.GTA_GAMES_DRAWABLES)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    val centerView = snapHelper.findSnapView(layoutManager)
                    val pos = layoutManager.getPosition(centerView!!)
                    gameName = RadiosMap.GTA_GAMES[pos]
                    setStationsSpinnerValues(gameName)
                    gameText.text = gameName
                    applyStateToDownloadButton()
                    applyStateToPlayButton()
                }
            }
        })
        (recyclerView.layoutManager as CarouselLayoutManager).smoothScrollToPosition(recyclerView, null, 2)
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
        unregisterReceiver(broadcastReceiver)
        if (isRadioPlayerServiceBound) unbindService(serviceConnection)
        super.onDestroy()
    }

    fun onRadioPlayerStateChanged() {
        applyStateToPlayButton()
    }
}

//TODO: Update spinner to be more beautiful
//TODO: Add a button to delete the station
//TODO: Update the UI to be more beautiful