package com.example.msap_device_utilities_kotlin_xml

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BatteryPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_battery_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val text1: TextView = findViewById(R.id.text1)

        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { intentFilter ->
            applicationContext.registerReceiver(null, intentFilter)
        }

        val batteryLevel: Float? = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        }

        text1.text = batteryLevel.toString()

        val batteryText: TextView = findViewById(R.id.batteryText)

        if (batteryLevel != null) {
            if (batteryLevel <= 25) {
                batteryText.text = "It is recommended to charge your device or enable battery saver"
            }
        }

        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL

        if (isCharging) {
            val text2: TextView = findViewById(R.id.text2)
            text2.text = "Device is charging"

            val chargePlug: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
            val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
            val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC
            val chargePlugText: TextView = findViewById(R.id.chargePlug)

            if (usbCharge) {
                chargePlugText.setTextColor(Color.GRAY)
                chargePlugText.text = "Device is charging with USB"
            }
            else {
                chargePlugText.setTextColor(Color.GREEN)
                chargePlugText.text = "Device is charging with AC"
            }
        }

        val bh: Boolean = status == BatteryManager.BATTERY_HEALTH_COLD
        val bh1: Boolean = status == BatteryManager.BATTERY_HEALTH_GOOD
        val bh2: Boolean = status == BatteryManager.BATTERY_HEALTH_DEAD
        val bh3: Boolean = status == BatteryManager.BATTERY_HEALTH_OVERHEAT
        val bh4: Boolean = status == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE
        val bh5: Boolean = status == BatteryManager.BATTERY_HEALTH_UNKNOWN

        val text3: TextView = findViewById(R.id.text3)
        if (bh) {
            text3.text = "Battery temperature is cold"
        }
        if (bh1) {
            text3.text = "Battery is good"
        }
        if (bh2) {
            text3.text = "Battery is dead"
            text3.setTextColor(Color.RED)
        }
        if (bh3) {
            text3.text = "Battery is overheating"
            text3.setTextColor(Color.RED)
        }
        if (bh4) {
            text3.text = "Battery has higher voltage"
            text3.setTextColor(Color.RED)
        }
        if (bh5) {
            text3.text = "Battery unknown"
        }
    }
}