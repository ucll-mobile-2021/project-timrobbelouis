package com.example.chef_louiardie

import android.content.Context
import android.app.Activity
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.net.URL

class RecipeAdapter(private val context: Context,
                    private val dataSource: ArrayList<String>) : BaseAdapter() {

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
        Thread.sleep(2_000)
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_recipe, parent, false)
        // Get title element
        val titleTextView = rowView.findViewById(R.id.recipe_list_title) as TextView
        // Get subtitle element
        //val subtitleTextView = rowView.findViewById(R.id.recipe_list_subtitle) as TextView
        // Get detail element
        var NoD = rowView.findViewById(R.id.NoD) as ImageView
        var NoGlut = rowView.findViewById(R.id.NoGlut) as ImageView
        val Vegan = rowView.findViewById(R.id.Vegan) as ImageView

        if(json.getBoolean("glutenFree")) NoD.visibility = View.VISIBLE
        if(json.getBoolean("dairyFree")) NoGlut.visibility =View.VISIBLE
        if(json.getBoolean("vegan")) Vegan.visibility = View.VISIBLE

        // Get thumbnail element
        val thumbnailImageView = rowView.findViewById(R.id.recipe_list_thumbnail) as ImageView
        //Setting all the view items
        titleTextView.text = json.getString("title")
       // subtitleTextView.text = "Lorem ipsum dolores est"
        //detailTextView.text = "Hier komt een label wow 69420"
        Picasso.get().load(json.getString("image")).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)
        return rowView
    }
}
