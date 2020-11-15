package com.example.chef_louiardie

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

class SearchRecipeActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_recipe)
        //String URL_ANSWER = "http//:myFake/" + IdString + "/Url";
        val input = findViewById<Tex>(R.layout.)
        val  url = "https://api.spoonacular.com/recipes/autocomplete?number=10&query=" +

    }

}