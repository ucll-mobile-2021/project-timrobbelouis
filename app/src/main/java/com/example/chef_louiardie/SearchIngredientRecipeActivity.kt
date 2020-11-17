package com.example.chef_louiardie

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class SearchIngredientRecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_ingredient_recipe)

        //add search button to look for a recipe
        val searchButton = findViewById<Button>(R.id.search_spec_recipe)
        searchButton.setOnClickListener {
            Thread {
                val input = findViewById<EditText>(R.id.input_search)
                val result = URL("https://api.spoonacular.com/recipes/findByIngredients?apiKey=47d5a6ad23494cf696007384ca0524cd&number=10&ingredients=${input.text}").readText()
                val resultJSON = JSONArray(result)
                val array = Array(resultJSON.length()){
                    //titel uit JSON string halen
                        i -> JSONObject(resultJSON.get(i).toString()).get("title")
                }
                //val url = JSONObject(result).getString("image")
                //Picasso.get().load(url).into(recipeInfoImage)
                //update listview with found results
                runOnUiThread {
                    //Update UI
                    val list = findViewById<ListView>(R.id.spec_list)

                    if(!array.isEmpty()) {
                        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
                        list.adapter = adapter
                    }else{
                        val arrayEmpti = Array(1){ i -> "No Results Were Found"
                        }
                        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayEmpti)
                        list.adapter = adapter
                    }
                    list.setOnItemClickListener { parent, view, position, id ->

                        val recipeId = JSONObject(resultJSON.get(position).toString()).get("id").toString()
                        val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                            putExtra("id", recipeId)
                        }
                        startActivity(intent)
                    }
                }
            }.start()
        }
    }
}
