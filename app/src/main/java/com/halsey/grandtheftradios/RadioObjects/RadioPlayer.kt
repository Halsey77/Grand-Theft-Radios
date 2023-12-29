package com.halsey.grandtheftradios.RadioObjects

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.util.Log
import java.util.*

class RadioPlayer(private val callback: RadioPlayerCallback, private val assetManager: AssetManager) {
    private var mediaPlayer: MediaPlayer? = null
    private var state = RadioPlayerState()
    private var staticSoundPlayer: MediaPlayer? = null
    private val maxStartingPointPortion = 0.8f

    class RadioPlayerState {
        var stationFilePath: String? = null
        var isPlaying = false
        var isPausing = false
    }

    interface RadioPlayerCallback {
        fun onRadioPlayerStateChanged(radioPlayerState: RadioPlayerState)
    }

    fun getRandomStaticRadioSoundFileDecriptor(): AssetFileDescriptor? {
        val staticFiles = assetManager.list("radio_statics")

        if (!staticFiles.isNullOrEmpty()) {
            val randomIndex = (staticFiles.indices).random()
            val randomStaticFile = staticFiles[randomIndex]

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

    fun insertStationToPlayer(stationFilePath: String) {
        if (state.isPlaying) { //if a station is playing
            if (state.stationFilePath == stationFilePath) { //if stations are the same
                pauseStation(stationFilePath)
            } else {  //if stations are different
                stopStation()   //stop the current station
                playStation(stationFilePath)    //play the new station
            }
        } else {    //if no station is playing or a station is paused
            if (state.stationFilePath == stationFilePath) {
                unpauseStation(stationFilePath)
            } else {
                playStation(stationFilePath)
            }
        }
    }

    private fun unpauseStation(filePath: String) {
        playStaticRadioSound {
            mediaPlayer?.start()
            setPlayingState(filePath)
            callback.onRadioPlayerStateChanged(state)
        }
    }

    private fun playStation(stationFilePath: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
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
                        setPlayingState(stationFilePath)
                        callback.onRadioPlayerStateChanged(state)
                    }
                }
            } catch (e: Exception) {
                Log.e("RadioPlayer", "Failed to play radio station")
                e.printStackTrace()
            }
        }
    }

    private fun pauseStation(filePath: String) {
        mediaPlayer?.pause()
        setPausingState(filePath)
        callback.onRadioPlayerStateChanged(state)
    }

    private fun stopStation() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        setStoppedState()
        callback.onRadioPlayerStateChanged(state)
    }

    private fun setPlayingState(filePath: String) {
        state.isPlaying = true
        state.isPausing = false
        state.stationFilePath = filePath
    }

    private fun setPausingState(filePath: String) {
        state.isPlaying = false
        state.isPausing = true
        state.stationFilePath = filePath
    }

    private fun setStoppedState() {
        state.isPlaying = false
        state.isPausing = false
        state.stationFilePath = null
    }

    fun isRadioStationPlaying(filePath: String): Boolean {
        return state.isPlaying && state.stationFilePath == filePath
    }

    fun onDestroy() {
        stopStation()
        staticSoundPlayer?.release()
    }
}
