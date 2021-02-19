package com.example.esp32_test

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.stream.Collectors

// toast example:
// Toast.makeText(applicationContext, "text", Toast.LENGTH_SHORT )

class MainActivity : AppCompatActivity() {
    //variable for toggling count
    //place to store the returned result of HTTP GET; see function httpGetFromURL
    private var turn  = 1 //turn is set to LOW at the start
    private var returnStr = " "

    //run update status command every 0.1seconds
    var handler = Handler()
    var runnable: Runnable? = null
    var delay = 100 //in milliseconds
    override fun onResume() {
        val stat: TextView = findViewById((R.id.statusIndicator))
        val statusURl = URL("http://192.168.86.36/S")
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            //command to run every 0.1 seconds
            updateStat(statusURl, stat)
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }

    //main commands
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get stuff from activity_main
        val stat: TextView = findViewById((R.id.statusIndicator))
        val toggle: Button = findViewById(R.id.toggle)
        val onBtn: Button = findViewById((R.id.onBtn))
        val offBtn: Button = findViewById((R.id.offBtn))
        val highURL = URL("http://192.168.86.36/H")
        val lowURL = URL("http://192.168.86.36/L")

        //asign actions to button clickes
        toggle.setOnClickListener {
            toggle(stat)
        }
        onBtn.setOnClickListener {
            httpGetFromURL(highURL)
        }
        offBtn.setOnClickListener {
            httpGetFromURL(lowURL)
        }
    }

    //function to update turn, status textview, and retreives and compares data from https
    private fun updateStat(statusURl: URL, sampleTextView: TextView) {
        httpGetFromURL(statusURl)
        if (returnStr == "Get HIGH<br>,HTTP/1.1 200 OK,Content-type:text/html,,") {
            sampleTextView.setText("Current Status: HIGH")
            turn = 0
        }
        if (returnStr == "Get LOW<br>,HTTP/1.1 200 OK,Content-type:text/html,,") {
            sampleTextView.setText("Current Status: Off")
            turn = 1
        }
        if (returnStr != "Get LOW<br>,HTTP/1.1 200 OK,Content-type:text/html,,"){
            if (returnStr != "Get HIGH<br>,HTTP/1.1 200 OK,Content-type:text/html,,"){
                sampleTextView.setText("Cannot Connect at this time")
            }
        }
    }

    //sends HTTP request to provided URL
    private fun httpGetFromURL(url: URL) {
        try {
            val executorService = Executors.newFixedThreadPool(2)
            executorService.execute {
                val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                try {
                    val input: InputStream = BufferedInputStream(urlConnection.inputStream)
                    val res = BufferedReader(InputStreamReader(input)).lines()
                        .collect(Collectors.toList())
                    Log.w("httpGetFromURLmsg", String.format("Get %s", res.joinToString(",")))
                    returnStr = String.format("Get %s", res.joinToString(","))

                } finally {
                    urlConnection.disconnect()
                }
            }
        } catch (e: Exception) {
            Log.w("httpGetFromURLmsg", "Failed", e)
        }
    }

    //toggle between on and off
    private fun toggle(stat: TextView) {
        try {
            val executorService = Executors.newFixedThreadPool(2)
            executorService.execute {
                val path = if (turn == 1) "H" else "L"
                val url = URL("http://192.168.86.36/".plus(path))
                turn = 1 - turn
                Handler(mainLooper).post {
                    //change status based on variable TURN
                    if (turn==0){
                        stat.setText("Current Status: On")
                    }else {
                        stat.setText("Current Status: Off")
                    }
                }

                val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                try {
                    val input: InputStream = BufferedInputStream(urlConnection.inputStream)
                    val res = BufferedReader(InputStreamReader(input)).lines()
                        .collect(Collectors.toList())
                    Log.w("FFFFF", String.format("Get %s", res.joinToString(",")))
                } finally {
                    urlConnection.disconnect()
                }
            }
        } catch (e: Exception) {
            Log.w("FFFFF", "Failed", e)
        }
    }
}
