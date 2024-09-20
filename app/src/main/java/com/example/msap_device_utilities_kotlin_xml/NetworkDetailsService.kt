package com.example.msap_device_utilities_kotlin_xml

import android.app.Service
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timer

class NetworkDetailsService: Service() {

    private var dlink: Int = 0

    private var timer: Timer = Timer()

    private fun measure(): Int {
        var connectivityManager: ConnectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkCapabilities: NetworkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) as NetworkCapabilities

        dlink = (networkCapabilities.linkDownstreamBandwidthKbps) / 1000

        return dlink
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Actions.Start.toString() -> start()
            Actions.Stop.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createTimerTask() = object : TimerTask() {
        override fun run() {
            var notification = NotificationCompat.Builder(applicationContext, "network_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Network Details")
                .setContentText("Current network speed is ${measure()} Mbps")
                .build()
            startForeground(1, notification)
        }
    }

    private fun start() {
        var timerTask = createTimerTask()
        timer.schedule(timerTask, 0, 3000)
    }

    enum class Actions {
        Start, Stop
    }
}