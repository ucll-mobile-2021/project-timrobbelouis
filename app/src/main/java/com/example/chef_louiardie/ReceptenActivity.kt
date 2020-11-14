package com.example.chef_louiardie

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

class ReceptenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recepten)

        var recept = intent.getStringExtra("recepten")

        val listView = findViewById<ListView>(R.id.listView)
        var resultArray = intent.getStringExtra("recepten")
        var resultJSON = JSONArray(resultArray)
        var array = Array(resultJSON.length()){
                i -> JSONObject(resultJSON.get(i).toString()).get("title")
        }


        if(resultArray != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
            listView.adapter = adapter
        }
    }
}