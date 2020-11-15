package com.example.chef_louiardie

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class SearchRecipeActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_recipe)

        val searchButton = findViewById<Button>(R.id.search_spec_recipe)
        searchButton.setOnClickListener {

            //request met input van de user
            Thread {
                val input = findViewById<EditText>(R.id.input_search)
                val result = URL("https://api.spoonacular.com/recipes/autocomplete?apiKey=47d5a6ad23494cf696007384ca0524cd&number=10&query=${input.text}").readText()
                val resultJSON = JSONArray(result)
                val array = Array(resultJSON.length()){
                    //titel uit JSON string halen
                    i -> JSONObject(resultJSON.get(i).toString()).get("title")
                }

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


