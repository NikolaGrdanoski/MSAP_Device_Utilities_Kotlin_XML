package com.example.msap_device_utilities_kotlin_xml

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class DeviceUtilitiesPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_device_utilities_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)

        btn1.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(applicationContext, BatteryPage::class.java)
            startActivity(intent)
        })

        btn2.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(applicationContext, USBPage::class.java)
            startActivity(intent)
        })

        btn3.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(applicationContext, FlashlightPage::class.java)
            startActivity(intent)
        })

        btn4.setOnClickListener(View.OnClickListener {
            var sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
                val intent: Intent = Intent(applicationContext, LightSensorPage::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(applicationContext, "No light sensor", Toast.LENGTH_SHORT).show()
            }
        })

        btn5.setOnClickListener(View.OnClickListener {
            var sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
                val intent: Intent = Intent(applicationContext, GyroscopePage::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(applicationContext, "No gyroscope", Toast.LENGTH_SHORT).show()
            }
        })

        btn6.setOnClickListener(View.OnClickListener {
            var sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
                val intent: Intent = Intent(applicationContext, ProximitySensorPage::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(applicationContext, "No proximity sensor", Toast.LENGTH_SHORT).show()
            }
        })

    }
}