package com.example.chef_louiardie

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.text.method.ScrollingMovementMethod
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.net.URL


class RecipeInformationActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        val apikey =  System.getProperties().getProperty("apikey")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_information)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Recipe Information"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val guideButton = findViewById<Button>(R.id.GuideButton)

        guideButton.setOnClickListener{
            val intent = Intent(this, GuideRecipeActivity::class.java).apply {
                putExtra("id",intent.getStringExtra("id"))
            }
            startActivity(intent)
        }



        val titel = findViewById<TextView>(R.id.titel)

        val ratingbar = findViewById<RatingBar>(R.id.ratingBar)

        val summary = findViewById<TextView>(R.id.summary)
        val recipeInfoImage = findViewById<ImageView>(R.id.recipeInfoImage)
//        summary.movementMethod = ScrollingMovementMethod()

        Thread {
            val result = URL("https://api.spoonacular.com/recipes/" + intent.getStringExtra("id") + "/information?apiKey="+ apikey).readText()
            runOnUiThread {
                titel.text = JSONObject(result).getString("title")
                //titel.text = JSONObject(result).getString("readyInMinutes")
                //titel.text = JSONObject(result).getString("glutenFree")
                //titel.text = JSONObject(result).getString("dairyFree")
                //titel.text = JSONObject(result).getString("vegan")
                //titel.text = JSONObject(result).getString("vegetarian")
                //titel.text = JSONObject(result).getString("pricePerServing")
                var score = JSONObject(result).getInt("spoonacularScore").toFloat()
                score /= 20
                ratingbar.rating = score.toFloat()

                summary.text = Html.fromHtml(
                    JSONObject(result).getString("summary"),
                    FROM_HTML_MODE_COMPACT
                )
                val url = JSONObject(result).getString("image")
                Picasso.get().load(url).into(recipeInfoImage)

            }
        }.start()

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}