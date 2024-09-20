package com.example.msap_device_utilities_kotlin_xml

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DeviceDetailsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_device_details_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView1: TextView = findViewById(R.id.text1)
        val textView2: TextView = findViewById(R.id.text2)
        val textView3: TextView = findViewById(R.id.text3)
        val textView4: TextView = findViewById(R.id.text4)
        val textView5: TextView = findViewById(R.id.text5)
        val textView6: TextView = findViewById(R.id.text6)
        val textView7: TextView = findViewById(R.id.text7)

        textView1.text = Build.DEVICE
        textView2.text = Build.BRAND
        textView3.text = Build.BOARD
        textView4.text = Build.HARDWARE
        textView5.text = Build.MANUFACTURER
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            textView6.text = Build.SOC_MODEL + " " + Build.SOC_MANUFACTURER
        }
        textView7.text = Build.VERSION.SDK_INT.toString()
    }
}