package com.example.chef_louiardie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavoritesActivity : AppCompatActivity() {
    val db = DataBaseHandler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        val apikey = System.getProperties().getProperty("apikey")
        super.onCreate(savedInstanceState)
        val actionbar = supportActionBar
        actionbar?.hide()
        setContentView(R.layout.favorites_page)
        doit()
        val deletbutton = findViewById<FloatingActionButton>(R.id.delet_all)
        deletbutton.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to delete all favorites?")
                .setCancelable(false)

                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    db.deleteData()
                    doit()
                }
            val alert = builder.create()
            alert.show()

        }
    }

    override fun onResume() {
        doit()
        super.onResume()
    }

    fun doit(){
        val listView = findViewById<ListView>(R.id.favoritesListView)
        listView.adapter = null

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

    }
}
