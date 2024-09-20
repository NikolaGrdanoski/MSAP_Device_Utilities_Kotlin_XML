package com.example.msap_device_utilities_kotlin_xml

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class NetworkDetailsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_network_details_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn1: Button = findViewById(R.id.startbtn)
        val btn2: Button = findViewById(R.id.stopbtn)

        val connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) as NetworkInfo

        btn1.setOnClickListener(View.OnClickListener {
            if (networkInfo.isConnected()) {
                Intent(applicationContext, NetworkDetailsService::class.java).also {
                it.action = NetworkDetailsService.Actions.Start.toString()
                startService(it)
                }
            }
            else {
                Toast.makeText(applicationContext, "WiFi unavailable", Toast.LENGTH_SHORT).show()
            }
        })

        btn2.setOnClickListener(View.OnClickListener {
            Intent(applicationContext, NetworkDetailsService::class.java).also {
                it.action = NetworkDetailsService.Actions.Stop.toString()
                startService(it)
            }
        })
    }
}