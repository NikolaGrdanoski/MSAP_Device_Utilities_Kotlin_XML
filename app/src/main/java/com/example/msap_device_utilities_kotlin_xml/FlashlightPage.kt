package com.example.msap_device_utilities_kotlin_xml

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FlashlightPage : AppCompatActivity() {
    private var flashlightStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_flashlight_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

        val button: Button = findViewById(R.id.btn1)

        button.setOnClickListener(View.OnClickListener {
            if (!flashlightStatus) {
                try {
                    cameraManager.setTorchMode(cameraId, true)
                    flashlightStatus = true
                    button.setText("Deactivate")
                } catch (e: CameraAccessException) {}
            }
            else {
                try {
                    cameraManager.setTorchMode(cameraId, false)
                    flashlightStatus = false
                    button.setText("Activate")
                } catch (e: CameraAccessException) {}
            }
        })
    }
}