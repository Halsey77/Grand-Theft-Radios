package com.halsey.grandtheftradios.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.halsey.grandtheftradios.R
import com.halsey.grandtheftradios.radio_objects.Radio
import com.halsey.grandtheftradios.radio_objects.RadioPlayerService

class RadioPlayerNotificationHelper(private val context: Context) {
    private val notiManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(context)
    private val CHANNEL_ID = "Grand Theft Radios"
    private var notificationBuilder: NotificationCompat.Builder? = null

    companion object {
        val INTENT_REQUEST_CODE = 69420
        val NOTIFICATION_ID = 69420
        val NOTIFICATION_INTENT =
            "com.halsey.grandtheftradios.notification.RadioPlayerNotificationHelper.NOTIFICATION_INTENT"
        val EXTRA_REQUEST_ACTION = "requestAction"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Grand Theft Radios"
        val descriptionText = "Listen to any GTA radio station!"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        notiManagerCompat.createNotificationChannel(channel)
    }

    fun updateNotificationAndShow(isPlaying: Boolean, radio: Radio?) {
        val notification = getNotification(isPlaying, radio)
        RequestPermission.requestNotificationPermission(context) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    fun getNotification(isPlaying: Boolean, radio: Radio?): Notification {
        val mediaStyle = getMediaStyle()
        val playPauseAction: NotificationCompat.Action = createActionButton(isPlaying)

        if (notificationBuilder == null) {
            notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        }

        setupNotificationBuilder(radio)
        notificationBuilder!!.clearActions()
        notificationBuilder!!.setStyle(mediaStyle)
            .addAction(playPauseAction)

        return notificationBuilder!!.build()
    }

    private fun setupNotificationBuilder(radio: Radio?) {
        notificationBuilder!!.setSmallIcon(R.drawable.ic_notification_icon)

        val contentTitle = radio?.name ?: "Grand Theft Radios"
        val contentText =
            if (radio != null) "You are listening to ${radio.name} from ${radio.game}" else "Choose a radio station to listen to"
        notificationBuilder!!.setContentTitle(contentTitle)
        notificationBuilder!!.setContentText(contentText)

        notificationBuilder!!.setPriority(NotificationCompat.PRIORITY_HIGH)
        notificationBuilder!!.setAutoCancel(false)
    }

    private fun getMediaStyle(): androidx.media.app.NotificationCompat.MediaStyle? =
        androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(null)
            .setShowActionsInCompactView(0)

    private fun createActionButton(isPlaying: Boolean): NotificationCompat.Action {
        return NotificationCompat.Action(
            if (isPlaying) R.drawable.ic_notification_pause else R.drawable.ic_notification_play,
            if (isPlaying) RadioPlayerService.ACTION_PAUSE else RadioPlayerService.ACTION_PLAY,
            getPendingIntent(if (isPlaying) RadioPlayerService.ACTION_PAUSE else RadioPlayerService.ACTION_PLAY)
        )
    }

    fun hideNotificationPlayer() {
        notiManagerCompat.cancel(NOTIFICATION_ID)
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(context, RadioPlayerService::class.java)
        intent.action = NOTIFICATION_INTENT
        intent.putExtra(EXTRA_REQUEST_ACTION, action)
        return PendingIntent.getService(context, INTENT_REQUEST_CODE, intent, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
    }
}