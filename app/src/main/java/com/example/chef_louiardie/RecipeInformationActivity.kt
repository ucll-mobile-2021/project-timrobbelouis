package com.example.chef_louiardie

import android.content.Intent
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.net.URL


class RecipeInformationActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_information)

        val guideButton = findViewById<Button>(R.id.GuideButton)

        guideButton.setOnClickListener{
            val intent = Intent(this, GuideRecipe::class.java).apply {
                putExtra("id",intent.getStringExtra("id"))
            }
            startActivity(intent)
        }



        val titel = findViewById<TextView>(R.id.titel)
        val summary = findViewById<TextView>(R.id.summary)
        val recipeInfoImage = findViewById<ImageView>(R.id.recipeInfoImage)
        summary.movementMethod = ScrollingMovementMethod()

        Thread {
            val result = URL("https://api.spoonacular.com/recipes/" + intent.getStringExtra("id") + "/information?apiKey=47d5a6ad23494cf696007384ca0524cd").readText()
            runOnUiThread {
                titel.text = JSONObject(result).getString("title")
                summary.text = Html.fromHtml(
                    JSONObject(result).getString("summary"),
                    FROM_HTML_MODE_COMPACT
                )
                val url = JSONObject(result).getString("image")
                Picasso.get().load(url).into(recipeInfoImage)

            }
        }.start()

    }

}