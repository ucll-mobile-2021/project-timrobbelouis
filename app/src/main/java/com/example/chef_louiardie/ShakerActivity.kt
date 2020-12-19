package com.example.chef_louiardie

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import org.json.JSONObject
import java.net.URL


import java.util.*
import kotlin.math.sqrt
class ShakerActivity : AppCompatActivity() {
        private var sensorManager: SensorManager? = null
        private var acceleration = 0f
        private var currentAcceleration = 0f
        private var lastAcceleration = 0f
        val apikey =  System.getProperties().getProperty("apikey")
        private var j = 0

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.randomshaker)
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            Objects.requireNonNull(sensorManager)!!.registerListener(sensorListener, sensorManager!!
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
            acceleration = 10f
            currentAcceleration = SensorManager.GRAVITY_EARTH
            lastAcceleration = SensorManager.GRAVITY_EARTH

        }
        fun goToRandomActivity() {
            j++
            if (j == 1) {
                Thread {

                    val result = URL("https://api.spoonacular.com/recipes/random?apiKey=" + apikey + "&number=1").readText()

                    runOnUiThread {
                        //Update UI
                        val resultJson = JSONObject(result)
                        val resultresultJson = resultJson.getJSONArray("recipes")
                        val id = resultresultJson.getJSONObject(0).get("id").toString()
                        val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                            putExtra("id", id)
                        }
                        startActivity(intent)
                    }
                }.start()
            }
        }


        private val sensorListener: SensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                var x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                lastAcceleration = currentAcceleration
                currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                val delta: Float = currentAcceleration - lastAcceleration
                acceleration = acceleration * 0.9f + delta

                if (acceleration > 12) {
                    Toast.makeText(applicationContext, "Shake event detected", Toast.LENGTH_SHORT).show()
                    goToRandomActivity()


                }

            }


            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        override fun onResume() {
            j = 0
            sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
                    Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
            )
            super.onResume()
        }
        override fun onPause() {
            sensorManager!!.unregisterListener(sensorListener)
            super.onPause()
        }
    }
