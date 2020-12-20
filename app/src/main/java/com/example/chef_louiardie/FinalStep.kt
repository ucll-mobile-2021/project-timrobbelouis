package com.example.chef_louiardie

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class FinalStep : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finish_step)
        val actionbar = supportActionBar
        actionbar!!.title = ""
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        val viewKonfetti = findViewById<KonfettiView>(R.id.viewKonfetti)


        findViewById<Button>(R.id.Home).setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.Share).setOnClickListener(){
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"I Just Made A Recipe Using The Chef Louiardee App!!")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }

        viewKonfetti.build()
            .addColors(Color.RED, Color.GREEN, Color.LTGRAY)
            .setDirection(400.0, 0.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(0f, viewKonfetti.width + 1200f, +50f, -50f)
            .streamFor(300, 3000L)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}