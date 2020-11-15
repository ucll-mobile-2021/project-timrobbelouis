package com.example.chef_louiardie

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL

class RecipeInformationActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_information)
        val titel = findViewById<TextView>(R.id.titel)
        val summary = findViewById<TextView>(R.id.summary)

        Thread {
            val result = URL("https://api.spoonacular.com/recipes/" + intent.getStringExtra("id") + "/summary?apiKey=47d5a6ad23494cf696007384ca0524cd").readText()
            runOnUiThread {
                titel.text = JSONObject(result).getString("title")
                summary.text = Html.fromHtml(JSONObject(result).getString("summary"),FROM_HTML_MODE_COMPACT)
            }
        }.start()

    }

}