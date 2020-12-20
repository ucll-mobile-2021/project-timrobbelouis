package com.example.chef_louiardie

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        val actionbar = supportActionBar
        actionbar?.hide()
        val properties = Properties()
        properties.put("apikey", "f57ead542f804aa4bb76336783b870e0")
        System.setProperties(properties)
        val apikey = System.getProperties().getProperty("apikey")
        // search by ingredient
        val searchIngredientRecipe = findViewById<ImageButton>(R.id.searchByIngredients)

        searchIngredientRecipe.setOnClickListener {
            val intent = Intent(this, SearchIngredientRecipeActivity::class.java).apply {
                putExtra("input",findViewById<AutoCompleteTextView>(R.id.searchByIngredientsInput).text.toString())
            }
            startActivity(intent)
        }
        // search recipe
        val searchRecipe = findViewById<ImageButton>(R.id.searchRecipe)

        searchRecipe.setOnClickListener {
            val intent = Intent(this, SearchRecipeActivity::class.java).apply {
                putExtra("input",findViewById<AutoCompleteTextView>(R.id.searchRecipeInput).text.toString())
            }
            startActivity(intent)
        }

        // search by random
        val searchRandomRecipe = findViewById<TextView>(R.id.getRandomRecipe)

        searchRandomRecipe.setOnClickListener {
            val intent = Intent(this, ShakerActivity::class.java)
            startActivity(intent)
        }
        val favoriteRecipebutton = findViewById<TextView>(R.id.favoriteRecipiesButton)
        favoriteRecipebutton.setOnClickListener{
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
        }
        val editorsChoiceImage1 = findViewById<ImageView>(R.id.EditorsChoiceImage1)
        val editorsChoiceImage2 = findViewById<ImageView>(R.id.EditorsChoiceImage2)
        val editorsChoiceImage3 = findViewById<ImageView>(R.id.EditorsChoiceImage3)
        val editorsChoiceImage4 = findViewById<ImageView>(R.id.EditorsChoiceImage4)
        val editorsTitle1 = findViewById<TextView>(R.id.EditorsChoiceTitle1)
        val editorsChoiceTitle2 = findViewById<TextView>(R.id.EditorsChoiceTitle2)
        val editorsChoiceTitle3 = findViewById<TextView>(R.id.EditorsChoiceTitle3)
        val editorsChoiceTitle4 = findViewById<TextView>(R.id.EditorsChoiceTitle4)
        Thread {
            val editorsChoiceRecipe1 = URL("https://api.spoonacular.com/recipes/535835/information?apiKey=" + apikey).readText()
            val editorsChoiceRecipe2 = URL("https://api.spoonacular.com/recipes/361374/information?apiKey=" + apikey).readText()
            val editorsChoiceRecipe3 = URL("https://api.spoonacular.com/recipes/640279/information?apiKey=" + apikey).readText()
            val editorsChoiceRecipe4 = URL("https://api.spoonacular.com/recipes/636989/information?apiKey=" + apikey).readText()
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
                editorsChoiceImage1.setOnClickListener {
                    val recipeId = JSONObject(editorsChoiceRecipe1).getString("id")
                    val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                        putExtra("id", recipeId)
                    }
                    startActivity(intent)
                }
                editorsChoiceImage2.setOnClickListener {
                    val recipeId = JSONObject(editorsChoiceRecipe2).getString("id")
                    val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                        putExtra("id", recipeId)
                    }
                    startActivity(intent)
                }
                editorsChoiceImage3.setOnClickListener {
                    val recipeId = JSONObject(editorsChoiceRecipe3).getString("id")
                    val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                        putExtra("id", recipeId)
                    }
                    startActivity(intent)
                }
                editorsChoiceImage4.setOnClickListener {
                    val recipeId = JSONObject(editorsChoiceRecipe4).getString("id")
                    val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                        putExtra("id", recipeId)
                    }
                    startActivity(intent)
                }
                editorsTitle1.setOnClickListener {
                    val recipeId = JSONObject(editorsChoiceRecipe1).getString("id")
                    val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                        putExtra("id", recipeId)
                    }
                    startActivity(intent)
                }
                editorsChoiceTitle2.setOnClickListener {
                    val recipeId = JSONObject(editorsChoiceRecipe2).getString("id")
                    val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                        putExtra("id", recipeId)
                    }
                    startActivity(intent)
                }
                editorsChoiceTitle3.setOnClickListener {
                    val recipeId = JSONObject(editorsChoiceRecipe3).getString("id")
                    val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                        putExtra("id", recipeId)
                    }
                    startActivity(intent)
                }
                editorsChoiceTitle4.setOnClickListener {
                    val recipeId = JSONObject(editorsChoiceRecipe4).getString("id")
                    val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                        putExtra("id", recipeId)
                    }
                    startActivity(intent)
                }
            }
        }.start()

    }
}