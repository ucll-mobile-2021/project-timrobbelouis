package com.example.chef_louiardie

import android.R.attr.path
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        val properties = Properties()
        properties.put("apikey","f57ead542f804aa4bb76336783b870e0")
        System.setProperties(properties)
        val apikey =  System.getProperties().getProperty("apikey")
        // search by ingredient
        val searchIngredientRecipe = findViewById<TextView>(R.id.searchByIngredients)

        searchIngredientRecipe.setOnClickListener{
            val intent = Intent(this, SearchIngredientRecipeActivity::class.java)
            startActivity(intent)
        }
        // search recipe
        val searchRecipe = findViewById<TextView>(R.id.searchRecipe)

        searchRecipe.setOnClickListener{
            val intent = Intent(this, SearchRecipeActivity::class.java)
            startActivity(intent)
        }

        // search by random
        val searchRandomRecipe = findViewById<TextView>(R.id.getRandomRecipe)

        searchRandomRecipe.setOnClickListener{

            Thread {

                val result = URL("https://api.spoonacular.com/recipes/random?apiKey=" + apikey +"&number=1").readText()

                runOnUiThread {
                    //Update UI


                    val resultJson = JSONObject(result)
                    val resultresultJson = resultJson.getJSONArray("recipes")
                    println(resultJson)
                    println(resultresultJson)
                    val id = resultresultJson.getJSONObject(0).get("id").toString()
                        println(id)
                     val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                            putExtra("id", id)
                       }
                      startActivity(intent)


                }
            }.start()

        }


    }
}