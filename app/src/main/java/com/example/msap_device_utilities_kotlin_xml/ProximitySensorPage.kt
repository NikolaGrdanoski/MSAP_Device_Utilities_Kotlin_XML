package com.example.msap_device_utilities_kotlin_xml

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.PowerManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProximitySensorPage : AppCompatActivity(), SensorEventListener {
    private var maxLux: Float = 0F
    private var currentLux = 0F

    private lateinit var sensorManager: SensorManager
    private var mLight: Sensor? = null

    private lateinit var text1: TextView

    private lateinit var powerManager: PowerManager
    private lateinit var lock: PowerManager.WakeLock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_proximity_sensor_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        text1 = findViewById(R.id.text1)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        lock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "simplewakelock:wakelocktag")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            currentLux = event.values[0]

            text1.text = "If sensor is covered, the screen will be disabled"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        mLight?.also { light -> sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    private fun brightness(brightness: Float): Any {
        return when (brightness.toInt()) {
            0 -> if (!lock.isHeld) lock.acquire(10*60*1000L /*10 minutes*/) else {}
            else -> if (lock.isHeld) lock.release() else {}
        }
    }
}