package com.halsey.grandtheftradios.RadioObjects

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast

class RadioDownloadManager(private val context: Context) {
    private var downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    private var downloadCompleteReceiver: BroadcastReceiver? = null
    private val onDownloadCompleteCallbacks: MutableList<(String) -> Unit> = mutableListOf()

    init {
        registerDownloadCompleteReceiver()
    }

    public fun addOnDownloadCompleteCallback(callback: (String) -> Unit) {
        onDownloadCompleteCallbacks.add(callback)
    }

    private fun registerDownloadCompleteReceiver() {
        downloadCompleteReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val downloadId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

                if (downloadId != null && downloadId != -1L) {
                    val url = getDownloadedUrl(downloadId)
                    onDownloadCompleteCallbacks.forEach { it(url) }
                }
            }
        }

        context.registerReceiver(downloadCompleteReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun getDownloadedUrl(downloadId: Long): String {
        val query = DownloadManager.Query()
        query.setFilterById(downloadId)

        val cursor = downloadManager.query(query)
        cursor.moveToFirst()

        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
        val uri = cursor.getString(columnIndex)

        cursor.close()

        return uri
    }

    private fun unregisterDownloadCompleteReceiver() {
        if (downloadCompleteReceiver != null) {
            context.unregisterReceiver(downloadCompleteReceiver)
            downloadCompleteReceiver = null
        }
    }

    private fun onDownloadComplete() {
        Toast.makeText(context, "Download finished", Toast.LENGTH_LONG).show()
    }

    fun startDownload(radio: Radio) {
        val request = DownloadManager.Request(Uri.parse(radio.url))
        request.setTitle("Downloading ${radio.name}")
        request.setDescription("Downloading radio station ${radio.name}...")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, radio.fileName)

        downloadManager.enqueue(request)
    }

    fun onDestroy() {
        unregisterDownloadCompleteReceiver()
    }

    public fun isStationBeingDownloaded(stationUrl: String): Boolean {
        return isStationInStatus(stationUrl,
            DownloadManager.STATUS_RUNNING or
                DownloadManager.STATUS_PAUSED or
                DownloadManager.STATUS_PENDING
        )
    }

    public fun isStationDownloaded(stationUrl: String): Boolean {
        return isStationInStatus(stationUrl, DownloadManager.STATUS_SUCCESSFUL)
    }

    private fun isStationInStatus(stationUrl: String, status: Int): Boolean {
        val query = DownloadManager.Query()
        query.setFilterByStatus(status)

        val cursor = downloadManager.query(query)
        while(cursor.moveToNext()) {
            val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_URI)
            val uri = cursor.getString(columnIndex)

            Log.v("RadioDownloadManager", "Checking $uri of status $status and ${uri == stationUrl}")
            if(uri == stationUrl) {
                cursor.close()
                return true
            }
        }

        cursor.close()
        return false
    }
}