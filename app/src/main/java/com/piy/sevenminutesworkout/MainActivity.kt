package com.piy.sevenminutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button.setOnClickListener {
            Toast.makeText(
                this,
                "Start an exercise", Toast.LENGTH_LONG
            ).show()

            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        llBMI.setOnClickListener {
            Toast.makeText(
                this,
                "Welcome to the BMI calculator.", Toast.LENGTH_LONG
            ).show()

            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }
    }
}