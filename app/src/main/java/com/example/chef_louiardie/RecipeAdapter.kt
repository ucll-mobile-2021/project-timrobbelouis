package com.example.chef_louiardie

import android.content.Context
import android.app.Activity
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.net.URL

class RecipeAdapter(private val context: Context,
                    private val dataSource: ArrayList<String>) : BaseAdapter() {

// Holy shit wat doet gij hier? Blijf er maar flink af dit is very important
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val apikey = System.getProperties().getProperty("apikey")
        val id = getItem(position)
        var json = JSONObject()
        Thread{
        var result = URL("https://api.spoonacular.com/recipes/$id/information?apiKey=$apikey").readText()
        json = JSONObject(result)
        }.start()
        while(json.length() == 0){
            Thread.sleep(100)
        }
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_recipe, parent, false)
        // Get title element
        val titleTextView = rowView.findViewById(R.id.recipe_list_title) as TextView

        // Allergenen informatie
        var NoDairy = rowView.findViewById(R.id.NoD) as ImageView
        var NoGlut = rowView.findViewById(R.id.NoGlut) as ImageView
        val Vegan = rowView.findViewById(R.id.Vegan) as ImageView

        // Get thumbnail element
        val thumbnailImageView = rowView.findViewById(R.id.recipe_list_thumbnail) as ImageView

        if(json.has("glutenFree")){
                if(json.getBoolean("glutenFree")){
                    NoDairy.visibility = View.VISIBLE
                }
        }
        if(json.has("dairyFree")){
            if(json.getBoolean("dairyFree")){
                NoGlut.visibility =View.VISIBLE
            }
        }
        if(json.has("vegan")){
            if(json.getBoolean("vegan")){
                Vegan.visibility = View.VISIBLE
            }
        }

        //Setting all the view items
        titleTextView.text = json.getString("title")
        Picasso.get().load(json.getString("image")).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)

        return rowView
    }
}
