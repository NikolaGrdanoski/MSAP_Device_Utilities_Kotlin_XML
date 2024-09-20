package com.example.msap_device_utilities_kotlin_xml

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LightSensorPage : AppCompatActivity(), SensorEventListener {
    private var maxLux: Float = 0F
    private var currentLux = 0F

    private lateinit var sensorManager: SensorManager
    private var mLight: Sensor? = null

    private lateinit var text1: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_light_sensor_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        text1 = findViewById(R.id.text1)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            currentLux = event.values[0]

            if (currentLux > maxLux) {
                maxLux = currentLux
            }

            text1.text = "Current lux is $currentLux\n${brightness(currentLux)}\nMaximum lux level measured is $maxLux"
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

    private fun brightness(brightness: Float): String {
        return when (brightness.toInt()) {
            0 -> "Black"
            in 1..10 -> "Dark"
            in 11..50 -> "Dim"
            in 51..5000 -> "Bright"
            in 5001..25000 -> "Very bright"
            else -> "Blinding Lights"
        }
    }
}