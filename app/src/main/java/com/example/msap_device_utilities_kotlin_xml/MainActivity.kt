package com.example.msap_device_utilities_kotlin_xml

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    private var sharedPrefFile = "com.example.android.MSAP_Device_Utilities_Kotlin"
    private var mPreferences: SharedPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE)
    private var THEME_KEY = "theme"
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_NETWORK_STATE), 0)

        //val pageTheme = findViewById(R.id.main)

        var mTheme = ContextCompat.getColor(this, R.color.white)

        val toolbar: Toolbar = findViewById(R.id.appbar)

        val main: LinearLayout = findViewById(R.id.main)

        /*listener = SharedPreferences.OnSharedPreferenceChangeListener() {
            fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

            }
        }*/

        listener =
            OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key === THEME_KEY) {
                    Toast.makeText(
                        this@MainActivity,
                        "Theme data changed in shared prefs",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        mTheme = mPreferences.getInt(THEME_KEY, mTheme)

        main.setBackgroundColor(mTheme)

        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)

        btn1.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(applicationContext, DeviceDetailsPage::class.java)
            startActivity(intent)
        })

        btn2.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(applicationContext, NetworkDetailsPage::class.java)
            startActivity(intent)
        })

        btn3.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(applicationContext, DeviceUtilitiesPage::class.java)
            startActivity(intent)
        })
    }

    override fun onPause() {
        super.onPause()
        var preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
        preferencesEditor.putInt("theme", 1)
    }
}