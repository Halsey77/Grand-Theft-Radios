package com.halsey.grandtheftradios.RadioObjects

import android.media.MediaPlayer
import android.util.Log

class RadioPlayer (private val callback: RadioPlayerCallback) {
    private var mediaPlayer: MediaPlayer? = null
    private var state = RadioPlayerState()

    class RadioPlayerState {
        var stationFilePath: String? = null
        var isPlaying = false
        var isPausing = false
    }

    interface RadioPlayerCallback {
        fun onRadioPlayerStateChanged(radioPlayerState: RadioPlayerState)
    }

    fun insertStationToPlayer(stationFilePath: String) {
        if (state.isPlaying) { //if a station is playing
            if (state.stationFilePath == stationFilePath) { //if stations are the same
                pauseStation(stationFilePath)
            } else {  //if stations are different
                stopStation()
                playStation(stationFilePath)
            }
        } else {    //if no station is playing or a station is paused
            if(state.stationFilePath == stationFilePath) {
                unpauseStation(stationFilePath)
            }
            else {
                playStation(stationFilePath)
            }
        }
    }

    private fun unpauseStation(filePath: String) {
        mediaPlayer?.start()
        setPlayingState(filePath)
        callback.onRadioPlayerStateChanged(state)
    }

    private fun playStation(stationFilePath: String) {
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        mediaPlayer?.reset()
        mediaPlayer?.apply {
            try {
                setDataSource(stationFilePath)
                prepareAsync()

                setOnPreparedListener {
                    start()
                    setPlayingState(stationFilePath)
                    callback.onRadioPlayerStateChanged(state)
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

    fun isRadioStationPaused(filePath: String): Boolean {
        return state.isPausing && state.stationFilePath == filePath
    }

    fun onDestroy() {
        stopStation()
    }
}
