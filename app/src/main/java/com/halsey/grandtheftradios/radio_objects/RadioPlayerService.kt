package com.halsey.grandtheftradios.radio_objects

import android.app.Service
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.halsey.grandtheftradios.notification.RadioPlayerNotificationHelper
import java.util.*

class RadioPlayerService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var state = RadioPlayerState()
    private var staticSoundPlayer: MediaPlayer? = null
    private val maxStartingPointPortion = 0.9f
    private val notificationManager: RadioPlayerNotificationHelper by lazy { RadioPlayerNotificationHelper(this) }
    private val binder = LocalBinder()
    private val assetsManager: AssetManager by lazy { assets }

    companion object {
        val RADIO_PLAYER_STATE_CHANGE =
            "com.halsey.grandtheftradios.radio_objects.RadioPlayerService.RADIO_PLAYER_STATE_CHANGE"
        val ACTION_PLAY = "Play"
        val ACTION_PAUSE = "Pause"
    }

    inner class LocalBinder : Binder() {
        fun getService(): RadioPlayerService = this@RadioPlayerService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    class RadioPlayerState {
        var radio: Radio? = null
        var stationFilePath: String? = null
        var isPlaying = false
        var isPausing = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            handleIntentAction(intent)
        }

        startForeground(
            RadioPlayerNotificationHelper.NOTIFICATION_ID,
            notificationManager.getNotification(state.isPlaying, state.radio)
        )

        return START_STICKY
    }

    private fun sendStateChangeToMainActivity() {
        val intent = Intent(RADIO_PLAYER_STATE_CHANGE)
        intent.putExtra("isPlaying", state.isPlaying)
        intent.putExtra("isPausing", state.isPausing)
        intent.putExtra("stationFilePath", state.stationFilePath)
        intent.putExtra("gameName", state.radio?.game)
        intent.putExtra("stationName", state.radio?.name)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun handleIntentAction(intent: Intent) {
        when (intent.action) {
            RadioPlayerNotificationHelper.NOTIFICATION_INTENT -> {
                handleIntentFromNotificationHelper(intent)
            }
        }
    }

    private fun handleIntentFromNotificationHelper(intent: Intent) {
        val requestAction = intent.getStringExtra(RadioPlayerNotificationHelper.EXTRA_REQUEST_ACTION)
        when (requestAction) {
            ACTION_PLAY -> {
                if (state.isPausing) {
                    unpauseStation(state.stationFilePath, state.radio)
                } else {
                    playStation(state.stationFilePath, state.radio)
                }
            }

            ACTION_PAUSE -> {
                pauseStation(state.stationFilePath, state.radio)
            }
        }
    }

    private fun showNotificationPlayer() {
        notificationManager.updateNotificationAndShow(state.isPlaying, state.radio)
    }

    private fun getRandomStaticRadioSoundFileDecriptor(): AssetFileDescriptor? {
        val staticFiles = assetsManager.list("radio_statics")

        if (!staticFiles.isNullOrEmpty()) {
            val randomStaticFile = staticFiles.random()
            return assetsManager.openFd("radio_statics/$randomStaticFile")
        }
        return null
    }

    private fun playStaticRadioSound(afterStaticOperation: () -> Unit = {}) {
        if (staticSoundPlayer == null) {
            staticSoundPlayer = MediaPlayer()
        }

        staticSoundPlayer?.apply {
            try {
                val staticRadioSoundFileDecriptor = getRandomStaticRadioSoundFileDecriptor()
                if (staticRadioSoundFileDecriptor == null) {
                    Log.e(
                        "RadioPlayer",
                        "Failed to play static radio sound because no static radio sound file was found"
                    )
                    return
                }

                setDataSource(
                    staticRadioSoundFileDecriptor.fileDescriptor,
                    staticRadioSoundFileDecriptor.startOffset,
                    staticRadioSoundFileDecriptor.length
                )
                prepareAsync()

                setOnPreparedListener {
                    start()
                }
                setOnCompletionListener {
                    staticSoundPlayer?.reset()
                    afterStaticOperation()
                }
            } catch (e: Exception) {
                Log.e("RadioPlayer", "Failed to play static radio sound")
                e.printStackTrace()
            }
        }
    }

    fun insertStationToPlayer(stationFilePath: String, radio: Radio?) {
        if (state.isPlaying) { //if a station is playing
            if (state.stationFilePath == stationFilePath) { //if stations are the same
                pauseStation(stationFilePath, radio)
            } else {  //if stations are different
                stopStation()   //stop the current station
                playStation(stationFilePath, radio)    //play the new station
            }
        } else {    //if no station is playing or a station is paused
            if (state.stationFilePath == stationFilePath) {
                unpauseStation(stationFilePath, radio)
            } else {
                playStation(stationFilePath, radio)
            }
        }
    }

    private fun unpauseStation(filePath: String?, radio: Radio?) {
        playStaticRadioSound {
            mediaPlayer?.start()
            setPlayingState(filePath, radio)
            showNotificationPlayer()
            sendStateChangeToMainActivity()
        }
    }

    private fun playStation(stationFilePath: String?, radio: Radio?) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }

        if (stationFilePath == null) return

        mediaPlayer?.reset()
        mediaPlayer?.apply {
            try {
                setDataSource(stationFilePath)
                prepareAsync()
                isLooping = true

                setOnPreparedListener {
                    playStaticRadioSound {
                        // choose a random time position in the file to start playing the station
                        val duration = mediaPlayer?.duration ?: 0
                        val randomStartPosition = Random().nextInt((duration * maxStartingPointPortion).toInt())
                        mediaPlayer?.seekTo(randomStartPosition)

                        start()
                        setPlayingState(stationFilePath, radio)
                        showNotificationPlayer()
                        sendStateChangeToMainActivity()
                    }
                }
            } catch (e: Exception) {
                Log.e("RadioPlayer", "Failed to play radio station")
                e.printStackTrace()
            }
        }
    }

    private fun pauseStation(filePath: String?, radio: Radio?) {
        mediaPlayer?.pause()
        setPausingState(filePath, radio)
        showNotificationPlayer()
        sendStateChangeToMainActivity()
    }

    private fun stopStation() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        setStoppedState()
        notificationManager.hideNotificationPlayer()
        sendStateChangeToMainActivity()
    }

    private fun setPlayingState(filePath: String?, radio: Radio?) {
        state.isPlaying = true
        state.isPausing = false
        state.stationFilePath = filePath
        state.radio = radio
    }

    private fun setPausingState(filePath: String?, radio: Radio?) {
        state.isPlaying = false
        state.isPausing = true
        state.stationFilePath = filePath
        state.radio = radio
    }

    private fun setStoppedState() {
        state.isPlaying = false
        state.isPausing = false
        state.stationFilePath = null
        state.radio = null
    }

    fun isRadioStationPlaying(filePath: String): Boolean {
        return isPlayerPlaying() && state.stationFilePath == filePath
    }

    fun isPlayerPlaying(): Boolean {
        return state.isPlaying
    }

    override fun onDestroy() {
        super.onDestroy()
        stopStation()
        staticSoundPlayer?.release()
    }
}
