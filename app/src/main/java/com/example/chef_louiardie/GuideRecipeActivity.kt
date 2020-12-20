package com.example.chef_louiardie

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class GuideRecipeActivity : AppCompatActivity() {
    var handler: Handler? = null
    var minute: TextView? = null
    var seconds: TextView? = null
    var milli_seconds: TextView? = null

    internal var MillisecondTime: Long = 0
    internal var StartTime: Long = 0
    internal var TimeBuff: Long = 0
    internal var UpdateTime = 0L

    internal var Seconds: Int = 0
    internal var Minutes: Int = 0
    internal var MilliSeconds: Int = 0

    internal var flag:Boolean=false
    internal var flag2:Boolean=false
    var startButton: Button? = null
    var nextstepbutton: Button? = null
    var previousStepButton: Button? = null
    var i = -1
    val apikey =  System.getProperties().getProperty("apikey")
    var stepsJA: JSONArray? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guide_recept)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Guide"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        Thread {
            val result = URL("https://api.spoonacular.com/recipes/" + intent.getStringExtra("id") + "/analyzedInstructions?apiKey=" + apikey).readText()

            val resultJSONArray = JSONArray(result)

            val j = resultJSONArray[0]
            val stepsJ = JSONObject(j.toString())

            val steps = stepsJ["steps"]
            stepsJA = JSONArray(steps.toString())


            runOnUiThread {

                startButton = findViewById(R.id.Play)
                previousStepButton = findViewById<Button>(R.id.previousStep)
                bindViews()
                nextstepbutton = findViewById<Button>(R.id.nextstep)
                updateStepUI(1)
                nextstepbutton?.setOnClickListener {
                    if(stepsJA?.length()!!-1 == i+1){
                        nextstepbutton?.text = "Finish"
                        val intent = Intent(this, FinalStep::class.java)
                        startActivity(intent)
                    }

                    updateStepUI(1)
                    previousStepButton?.visibility = View.VISIBLE


                }

                previousStepButton?.setOnClickListener {
                    nextstepbutton?.text = "Next Step"
                    if(nextstepbutton?.visibility == View.INVISIBLE){
                        nextstepbutton?.visibility = View.VISIBLE
                    }
                    if(i <= 1){
                        previousStepButton?.visibility = View.INVISIBLE
                    }
                    updateStepUI(-1)
                }
            }
        }.start()
    }

    var runnable: Runnable = object : Runnable {

        override fun run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime

            UpdateTime = TimeBuff + MillisecondTime

            Seconds = (UpdateTime / 1000).toInt()

            Minutes = Seconds / 60

            Seconds = Seconds % 60

            MilliSeconds = (UpdateTime % 1000).toInt()


            if (Minutes.toString().length < 2) {
                minute?.text = "0" + Minutes.toString() + ":"
            } else {
                minute?.text = Minutes.toString()
            }
            if (Seconds.toString().length < 2) {
                seconds?.text = "0" + Seconds.toString() + ":"
            } else {
                seconds?.text = Seconds.toString()
            }

            milli_seconds?.text = MilliSeconds.toString()

            handler?.postDelayed(this, 0)
        }

    }

    private fun bindViews() {
        minute = findViewById(R.id.minute)
        seconds = findViewById(R.id.seconds)
        milli_seconds = findViewById(R.id.milli_second)
        startButton?.setOnClickListener {
            if (flag){
                startButton?.text = "START TIMER"
                handler?.removeCallbacks(runnable)
                flag=false
            }else{
                startButton?.text = "STOP TIMER"
                StartTime = SystemClock.uptimeMillis()
                handler?.postDelayed(runnable, 0)
                flag=true
            }
        }

        handler = Handler()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SetTextI18n")
    public fun updateStepUI(x: Int) {
        i += x
        while (i == (stepsJA?.length()!! - 1)) {
            i-=x
        }
        val step = stepsJA?.get(i)
        val stepJ = JSONObject(step.toString())
        val number = stepJ["number"]
        val explimination = stepJ["step"]
        val ingredients = stepJ["ingredients"]

        val numberstep = findViewById<TextView>(R.id.number)
        val ingredientslist = findViewById<ListView>(R.id.ingred_list)
        val explanation = findViewById<TextView>(R.id.explanation)

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

}