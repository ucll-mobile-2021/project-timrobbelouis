package com.example.chef_louiardie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.json.JSONObject
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
                val resultJson = JSONObject(result)
                val resultresultJson = resultJson.getJSONArray("results")
//                val end = resultresultJson.length()-1
//                for(x in 0..end){
//                    println(JSONObject(resultresultJson.get(x).toString()).get("image"))
//                }
                val intent = Intent(this, ReceptenActivity::class.java).apply {
                    putExtra("recepten", resultresultJson.toString())
                }
                startActivity(intent)
            }.start()
        }

        val searchRecipe = findViewById<TextView>(R.id.searchRecipe)

        searchRecipe.setOnClickListener{
            val intent = Intent(this, SearchRecipeActivity::class.java)
            startActivity(intent)
        }


    }
}