package com.example.chef_louiardie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class SearchIngredientRecipeActivity: AppCompatActivity()  {
    val apikey =  System.getProperties().getProperty("apikey")

    var array= emptyArray<String>()
    var resultJSON = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        var of = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_recipe)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Search Recipe"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val input = findViewById<EditText>(R.id.input_search)
        val previnput = intent.getStringExtra("input")
        input.setText(previnput)
        search(input.text.toString())
        Thread.sleep(250)
        doit(of)



        val searchButton = findViewById<Button>(R.id.search_spec_recipe)
        searchButton.setOnClickListener {
            search(input.text.toString())
            Thread.sleep(250)
            doit(of)
        }

        val next = findViewById<Button>(R.id.NextPage)
        val prev = findViewById<Button>(R.id.PreviousPage)
        next.setOnClickListener{
            next.visibility = View.INVISIBLE
            prev.visibility = View.VISIBLE
            of += 10
            doit(of)
        }

        prev.setOnClickListener{
            prev.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE
            of -= 10
            doit(of)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    //request met input van de user
    private fun doit(offset : Int){
        println("in od it " + array.size)
        var arrayTemp= emptyArray<String>()
        if(array.size >= 10 + offset) {
            arrayTemp = array.sliceArray(0 + offset..9 + offset)
        }

        val list = findViewById<ListView>(R.id.spec_list)
        if(!arrayTemp.isEmpty()) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTemp)
            list.adapter = adapter
        }else{
            val arrayEmpti = Array(1){ i -> "No Results Were Found"
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayEmpti)
            list.adapter = adapter
        }
        list.setOnItemClickListener { parent, view, position, id ->
            val recipeId = JSONObject(resultJSON.get(position).toString()).get("id").toString()
            val intent = Intent(this, RecipeInformationActivity::class.java).apply {
                putExtra("id", recipeId)
            }
            startActivity(intent)
        }


    }

    private fun search(input : String){
        if(!input.isEmpty()) {
            Thread {
                val result = URL("https://api.spoonacular.com/recipes/findByIngredients?apiKey="+ apikey +"&number=20&ingredients=${input}").readText()


                resultJSON = JSONArray(result)
                println(result.toString())
                array = Array(resultJSON.length()) {
                    //titel uit JSON string halen
                        i ->
                    JSONObject(resultJSON.get(i).toString()).get("title").toString()
                }
                println("in thread " + array.size)
            }.start()
        }
    }


}


