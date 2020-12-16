package com.example.chef_louiardie

import android.annotation.SuppressLint
import android.app.ActionBar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class GuideRecipeActivity : AppCompatActivity() {
    var i = 0
    val apikey =  System.getProperties().getProperty("apikey")
    override fun onCreate(savedInstanceState: Bundle?) {

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Guide"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.guide_recept)

        val nextstepbutton = findViewById<Button>(R.id.nextstep)

            updateStepUI(1)
            nextstepbutton.setOnClickListener {


                updateStepUI(1)



            }



    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SetTextI18n")
    public fun updateStepUI(x: Int) {
        Thread {
            val result = URL("https://api.spoonacular.com/recipes/" + intent.getStringExtra("id") + "/analyzedInstructions?apiKey="+ apikey).readText()
            println(result)
            val resultJSONArray = JSONArray(result)

            val j = resultJSONArray[0]
            val stepsJ = JSONObject(j.toString())

            val steps = stepsJ["steps"]
            val stepsJA = JSONArray(steps.toString())
            val step = stepsJA[i]
            i += x
            val stepJ = JSONObject(step.toString())
            val number = stepJ["number"]
            val explimination = stepJ["step"]
            val ingredients = stepJ["ingredients"]


            val numberstep = findViewById<TextView>(R.id.number)
            val ingredientslist = findViewById<ListView>(R.id.ingred_list)
            val explanation = findViewById<TextView>(R.id.explanation)


            runOnUiThread {

                val ingredientsJA = JSONArray(ingredients.toString())
                val array = Array(ingredientsJA.length()) {
                    //name uit JSON string halen
                    i ->
                    JSONObject(ingredientsJA.get(i).toString()).get("name")
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)

                ingredientslist.adapter = adapter
                numberstep.text = "Step $number"
                explanation.text = explimination.toString()


            }
        }.start()
    }
}