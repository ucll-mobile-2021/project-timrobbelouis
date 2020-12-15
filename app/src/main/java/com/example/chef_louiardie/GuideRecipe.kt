package com.example.chef_louiardie

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class GuideRecipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guide_recept)

        Thread {
            val result = URL("https://api.spoonacular.com/recipes/" + intent.getStringExtra("id") + "/analyzedInstructions?apiKey=47d5a6ad23494cf696007384ca0524cd").readText()

            var resultJSONArray = JSONArray(result)
            var j  = resultJSONArray[0]
            var stepsJ = JSONObject(j.toString())

            var steps = stepsJ["steps"]
            var stepsJA = JSONArray(steps.toString())
            var step = stepsJA[0]
            var stepJ = JSONObject(step.toString())
            var number = stepJ["number"]
            var explimination = stepJ["step"]
            var ingredients = stepJ["ingredients"]
            var equipment = stepJ["equipment"]

            print( number)
            print("\n")
            print( explimination )
            print("\n")
            print( ingredients )
            print("\n")
            print( equipment )
            // runOnUiThread {
           //     titel.text = JSONObject(result).getString("title")
            //    summary.text = Html.fromHtml(
            //        JSONObject(result).getString("summary"),
            //        Html.FROM_HTML_MODE_COMPACT
            //    )
            //    val url = JSONObject(result).getString("image")
            //    Picasso.get().load(url).into(recipeInfoImage)

            //}
        }.start()

    }

}