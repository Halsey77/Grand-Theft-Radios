package com.halsey.grandtheftradios.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.halsey.grandtheftradios.MainActivity

class RequestPermission() {
    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                            Toast.makeText(context, "Player need notification permission to work", Toast.LENGTH_LONG)
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