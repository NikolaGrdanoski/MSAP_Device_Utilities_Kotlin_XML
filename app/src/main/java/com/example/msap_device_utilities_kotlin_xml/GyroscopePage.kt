package com.example.msap_device_utilities_kotlin_xml

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class GyroscopePage : AppCompatActivity(), SensorEventListener {
    val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val listener: Listener? = null

    interface Listener {
        // create method with all 3
        // axis translation as argument
        fun onTranslation(tx: Float, ty: Float, ts: Float)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gyroscope_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun onTranslation(tx: Float, ty: Float, ts: Float) {
            // set the color red if the device moves in positive x axis
            if (tx > 1.0f) {
                window.decorView.setBackgroundColor(Color.RED)
            } else if (tx < -1.0f) {
                window.decorView.setBackgroundColor(Color.BLUE)
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (listener != null) {
            // pass the three floats in listener on translation of axis
            if (event != null) {
                listener.onTranslation(event.values[0], event.values[1], event.values[2])
            };
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
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
}