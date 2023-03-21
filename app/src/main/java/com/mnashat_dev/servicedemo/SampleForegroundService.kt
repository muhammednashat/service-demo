package com.mnashat_dev.servicedemo


import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.mnashat_dev.servicedemo.ForegroundServiceActivity.Companion.ACTION_STOP

class SampleForegroundService: Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.action != null && intent.action!!.equals(
                ACTION_STOP, ignoreCase = true)) {
            stopForeground(STOP_FOREGROUND_DETACH)
            stopSelf()
            return START_NOT_STICKY
        }

        generateForegroundNotification()
        return START_STICKY
    }



    private var notification: Notification? = null
    var notificationManager: NotificationManager? = null
    private val notificationId = 123

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateForegroundNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val intent = intentNewActivity(this, ForegroundServiceActivity())

            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, 0)

            if (notificationManager == null) {
                notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val notificationChannel =
                    NotificationChannel("service_channel", "Service Notifications",
                        NotificationManager.IMPORTANCE_MIN)
                notificationChannel.enableLights(false)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
                notificationManager?.createNotificationChannel(notificationChannel)
            }

            val builder = NotificationCompat.Builder(this, "service_channel").apply {
                setContentTitle(StringBuilder(resources.getString(R.string.app_name)).append(" service is running").toString())
                    setTicker(StringBuilder(resources.getString(R.string.app_name)).append("service is running").toString())
                    setContentText("Touch to Stop It")
                    setSmallIcon(R.drawable.ic_baseline_alarm)
                    priority = NotificationCompat.PRIORITY_LOW
                    setOnlyAlertOnce(true)
                    setContentIntent(pendingIntent)
                    setOngoing(true)
            }


            notification = builder.build()
            startForeground(notificationId, notification)
        }

    }
}