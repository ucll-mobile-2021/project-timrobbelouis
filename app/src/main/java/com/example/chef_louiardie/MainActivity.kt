package com.example.chef_louiardie

import android.R.attr.path
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
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
        properties.put("apikey","61c6cdff38df4291862c41a13f16e51e")
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
        Thread {
            val editorsChoiceRecipe1 = URL("https://api.spoonacular.com/recipes/535835/information?apiKey="+ apikey).readText()
            val editorsChoiceRecipe2 = URL("https://api.spoonacular.com/recipes/361374/information?apiKey="+ apikey).readText()
            val editorsChoiceRecipe3 = URL("https://api.spoonacular.com/recipes/640279/information?apiKey="+ apikey).readText()
            val editorsChoiceRecipe4 = URL("https://api.spoonacular.com/recipes/636989/information?apiKey="+ apikey).readText()
            val editorsChoiceImage1 = findViewById<ImageView>(R.id.EditorsChoiceImage1)
            val editorsChoiceImage2 = findViewById<ImageView>(R.id.EditorsChoiceImage2)
            val editorsChoiceImage3 = findViewById<ImageView>(R.id.EditorsChoiceImage3)
            val editorsChoiceImage4 = findViewById<ImageView>(R.id.EditorsChoiceImage4)
            val editorsTitle1 = findViewById<TextView>(R.id.EditorsChoiceTitle1)
            val editorsChoiceTitle2 = findViewById<TextView>(R.id.EditorsChoiceTitle2)
            val editorsChoiceTitle3 = findViewById<TextView>(R.id.EditorsChoiceTitle3)
            val editorsChoiceTitle4 = findViewById<TextView>(R.id.EditorsChoiceTitle4)
            runOnUiThread {
                editorsTitle1.text = JSONObject(editorsChoiceRecipe1).getString("title")
                editorsChoiceTitle2.text = JSONObject(editorsChoiceRecipe2).getString("title")
                editorsChoiceTitle3.text = JSONObject(editorsChoiceRecipe3).getString("title")
                editorsChoiceTitle4.text = JSONObject(editorsChoiceRecipe4).getString("title")
                val url = JSONObject(editorsChoiceRecipe1).getString("image")
                Picasso.get().load(url).into(editorsChoiceImage1)
                val url2 = JSONObject(editorsChoiceRecipe2).getString("image")
                Picasso.get().load(url2).into(editorsChoiceImage2)
                val url3 = JSONObject(editorsChoiceRecipe3).getString("image")
                Picasso.get().load(url3).into(editorsChoiceImage3)
                val url4 = JSONObject(editorsChoiceRecipe4).getString("image")
                Picasso.get().load(url4).into(editorsChoiceImage4)
            }
        }.start()


    }
}