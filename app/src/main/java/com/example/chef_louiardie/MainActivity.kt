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
        val button = findViewById<Button>(R.id.button)
        //voorbeeld code om request te sturen en values op de app te tonen
        button.setOnClickListener {
            Thread {
                //Do some Network Request
                val result = URL("https://api.spoonacular.com/recipes/complexSearch?apiKey=47d5a6ad23494cf696007384ca0524cd&query=pasta&excludeIngredients=tuna").readText()
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

                val result = URL("https://api.spoonacular.com/recipes/random?apiKey=47d5a6ad23494cf696007384ca0524cd&number=1").readText()

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