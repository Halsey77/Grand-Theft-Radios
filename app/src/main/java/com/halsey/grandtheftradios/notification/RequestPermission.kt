package com.halsey.grandtheftradios.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.halsey.grandtheftradios.MainActivity

class RequestPermission() {
    companion object {
        fun requestNotificationPermission(context: Context, afterPermissionGranted: () -> Unit) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val activity = context as MainActivity
                val requestPermissionLauncher =
                    activity.registerForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { isGranted: Boolean ->
                        if (isGranted) {
                            afterPermissionGranted()
                        } else {
                            Toast.makeText(context, "Player cannot be shown in notification bar", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return
            } else {
                afterPermissionGranted()
            }
        }
    }
}