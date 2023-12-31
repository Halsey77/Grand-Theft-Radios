package com.halsey.grandtheftradios.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.halsey.grandtheftradios.MainActivity
import com.halsey.grandtheftradios.R
import com.halsey.grandtheftradios.radio_objects.Radio
import com.halsey.grandtheftradios.radio_objects.RadioPlayer

class RadioPlayerNotificationHelper(private val context: Context) {
    private val notiManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(context)
    private val CHANNEL_ID = "Grand Theft Radios"
    private val NOTIFICATION_ID = 69420
    private var notificationBuilder: NotificationCompat.Builder? = null

    companion object {
        val INTENT_REQUEST_CODE = 69420
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Grand Theft Radios"
        val descriptionText = "Listen to any GTA radio station!"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        notiManagerCompat.createNotificationChannel(channel)
    }

    @SuppressLint("MissingPermission")
    fun showNotificationPlayer(isPlaying: Boolean, radio: Radio?) {
        val mediaStyle = androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(null)
            .setShowActionsInCompactView(0)
        val playPauseAction: NotificationCompat.Action = NotificationCompat.Action(
            if (isPlaying) R.drawable.ic_notification_pause else R.drawable.ic_notification_play,
            if (isPlaying) RadioPlayer.ACTION_PAUSE else RadioPlayer.ACTION_PLAY,
            getPendingIntent(if (isPlaying) RadioPlayer.ACTION_PAUSE else RadioPlayer.ACTION_PLAY)
        )

        if (notificationBuilder == null) {
            notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)

            notificationBuilder!!.setSmallIcon(R.drawable.ic_notification_icon)
            notificationBuilder!!.setContentTitle(radio?.name)
            notificationBuilder!!.setContentText("You are listening to ${radio?.name} from ${radio?.game}")
            notificationBuilder!!.setPriority(NotificationCompat.PRIORITY_LOW)
            notificationBuilder!!.setOngoing(true)
            notificationBuilder!!.setAutoCancel(false)
        }

        notificationBuilder!!.clearActions()
        notificationBuilder!!.setStyle(mediaStyle)
            .addAction(playPauseAction)

        val notification = notificationBuilder!!.build()
        RequestPermission.requestNotificationPermission(context) {
            notiManagerCompat.notify(NOTIFICATION_ID, notification)
        }
    }

    fun hideNotificationPlayer() {
        notiManagerCompat.cancel(NOTIFICATION_ID)
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.action = action
        intent.putExtra("requestCode", INTENT_REQUEST_CODE)
        return PendingIntent.getActivity(context, INTENT_REQUEST_CODE, intent, FLAG_IMMUTABLE)
    }
}