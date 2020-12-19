package com.example.chef_louiardie

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.squareup.picasso.Picasso
import org.json.JSONArray
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


        val id = intent.getStringExtra("id")
        val context = this
        var db = DataBaseHandler(context)
                if (id != null) {
                    println(db.contains(id))
                    if(db.contains(id)){
                        findViewById<ImageButton>(R.id.favorite_empty).setBackgroundResource(R.drawable.heart_black)
                    }
                }
        val favorite = findViewById<ImageButton>(R.id.favorite_empty)
        favorite.setOnClickListener {
        if (id != null) {
            if (db.contains(id)) {
                db.deleteRecipe(id)
                favorite.setBackgroundResource(R.drawable.heart_white)
            }
            else {
                db.insertData(id)
                favorite.setBackgroundResource(R.drawable.heart_black)
            }
        }

        }
        val titel = findViewById<TextView>(R.id.titel)

        val ratingbar = findViewById<RatingBar>(R.id.ratingBar)

        val recipeInfoImage = findViewById<ImageView>(R.id.recipeInfoImage)


        Thread {
            val result = URL("https://api.spoonacular.com/recipes/" + intent.getStringExtra("id") + "/information?apiKey="+ apikey).readText()
            val steps = JSONObject(result).getJSONArray("analyzedInstructions")

            guideButton.setOnClickListener{
                val intent = Intent(this, GuideRecipeActivity::class.java).apply {
                    putExtra("id", intent.getStringExtra("id"))
                }
                startActivity(intent)
            }


            runOnUiThread {
                if( !steps.isNull(0)) {
                    guideButton.visibility = View.VISIBLE
                }
                titel.text = JSONObject(result).getString("title")
                findViewById<TextView>(R.id.time).text = JSONObject(result).getInt("readyInMinutes").toString() + " min"
                if(JSONObject(result).getBoolean("glutenFree")) findViewById<ImageView>(R.id.NoGlut).visibility =View.VISIBLE
                if(JSONObject(result).getBoolean("dairyFree")) findViewById<ImageView>(R.id.NoD).visibility =View.VISIBLE
                if(JSONObject(result).getBoolean("vegan")) findViewById<ImageView>(R.id.Vegan).visibility =View.VISIBLE

                val ingredientsJA = JSONArray(JSONObject(result).getString("extendedIngredients"))
                var score = JSONObject(result).getInt("spoonacularScore").toFloat()
                score /= 20
                ratingbar.rating = score.toFloat()

                val ingredientslist = findViewById<ListView>(R.id.Ingridients)
                val array = Array(ingredientsJA.length()) {
                    //original uit JSON string halen
                    i ->
                    JSONObject(ingredientsJA.get(i).toString()).getString("original")
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)

                ingredientslist.adapter = adapter


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