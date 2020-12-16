package com.example.chef_louiardie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)


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

                val result = URL("https://api.spoonacular.com/recipes/random?apiKey=f57ead542f804aa4bb76336783b870e0&number=1").readText()

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