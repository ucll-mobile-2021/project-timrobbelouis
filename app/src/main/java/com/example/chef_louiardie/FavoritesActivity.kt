package com.example.chef_louiardie

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItem
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.get
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val apikey = System.getProperties().getProperty("apikey")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_page)
        val listView = findViewById<ListView>(R.id.favoritesListView)
        val context = this
        val db = DataBaseHandler(context)
        val favoritesArrayList = db.readData()
        if(favoritesArrayList.size != 0) {
            val adapter = RecipeAdapter(this, favoritesArrayList as ArrayList<String>)
            listView.adapter = adapter
            listView.setOnItemClickListener { parent, view, position, id ->
                val recipeId = adapter.getItem(position).toString() // The item that was clicked
                print(recipeId)
                val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                    putExtra("id", recipeId)
                }
                startActivity(intent)
            }
        }
        val deletbutton = findViewById<Button>(R.id.delet_all)
        deletbutton.setOnClickListener {
            db.deleteData()
        }

    }
}
