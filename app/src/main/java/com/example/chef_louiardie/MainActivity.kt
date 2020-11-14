package com.example.chef_louiardie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.net.URL
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Thread {
                //Do some Network Request
                 val result =
                    URL("https://api.spoonacular.com/recipes/complexSearch?apiKey=47d5a6ad23494cf696007384ca0524cd&query=pasta&excludeIngredients=tuna").readText()
                println(result)
            }.start()


        }

    }
}