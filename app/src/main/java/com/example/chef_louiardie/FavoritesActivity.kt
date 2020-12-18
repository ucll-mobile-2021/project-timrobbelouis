package com.example.chef_louiardie

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val apikey = System.getProperties().getProperty("apikey")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites)
        var listView = findViewById<ListView>(R.id.favoritesListView)
        val favoritesArrayList = FavoritesDb.favorites

        val adapter = RecipeAdapter(this, favoritesArrayList as ArrayList<String>)
        listView.adapter = adapter


    }
}
