package com.halsey.grandtheftradios.radio_objects

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.util.Log
import com.halsey.grandtheftradios.notification.RadioPlayerNotificationHelper
import java.util.*

class RadioPlayer(private val callback: RadioPlayerCallback, private val assetManager: AssetManager, context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var state = RadioPlayerState()
    private var staticSoundPlayer: MediaPlayer? = null
    private val maxStartingPointPortion = 0.8f
    private val notificationManager: RadioPlayerNotificationHelper = RadioPlayerNotificationHelper(context)

    companion object {
        val ACTION_PLAY = "Play"
        val ACTION_PAUSE = "Pause"
    }

    class RadioPlayerState {
        var radio: Radio? = null
        var stationFilePath: String? = null
        var isPlaying = false
        var isPausing = false
    }

    interface RadioPlayerCallback {
        fun onRadioPlayerStateChanged(radioPlayerState: RadioPlayerState)
    }

    fun handleIntentAction(action: String) {
        when (action) {
            ACTION_PLAY -> {
                unpauseStation(state.stationFilePath, state.radio)
            }

            ACTION_PAUSE -> {
                pauseStation(state.stationFilePath, state.radio)
            }
        }
        showNotificationPlayer()
    }

    private fun showNotificationPlayer() {
        notificationManager.showNotificationPlayer(state.isPlaying, state.radio)
    }

    private fun getRandomStaticRadioSoundFileDecriptor(): AssetFileDescriptor? {
        val staticFiles = assetManager.list("radio_statics")

        if (!staticFiles.isNullOrEmpty()) {
            val randomStaticFile = staticFiles.random()
            return assetManager.openFd("radio_statics/$randomStaticFile")
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
            callback.onRadioPlayerStateChanged(state)
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
                        callback.onRadioPlayerStateChanged(state)
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
        callback.onRadioPlayerStateChanged(state)
    }

    private fun stopStation() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        setStoppedState()
        notificationManager.hideNotificationPlayer()
        callback.onRadioPlayerStateChanged(state)
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

    fun onDestroy() {
        stopStation()
        staticSoundPlayer?.release()
    }
}
