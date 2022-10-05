package com.piy.sevenminutesworkout

import android.annotation.SuppressLint
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_exercise.*
import java.lang.Error

class ExerciseActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseProgress = 0
    private var exerciseIndex = -1
    val workoutParams = WorkoutGenerator.generateNewWorkout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        setupRestView()

    }

    override fun onDestroy() {
        if(restTimer!= null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if(exerciseTimer!= null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        super.onDestroy()
    }

    private fun setRestProgressBar() {
        progress_bar_rest.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                restProgress ++
                progress_bar_rest.progress = 10 - restProgress
                tv_timer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                println("Time is up!")
                Toast.makeText(this@ExerciseActivity, "Now we start the exercise!", Toast.LENGTH_SHORT).show()
                var size: Int = workoutParams["exerciseGifs"]?.size ?: throw Error("bad data!")

                if(exerciseIndex < size - 1 ) {
                    exerciseIndex ++
                } else {
                    exerciseIndex = 0
                }

                setupExerciseView()
            }
        }.start()
    }

    private fun  setupRestView () {
        ll_exercise_view.visibility = View.GONE
        ll_rest_view.visibility = View.VISIBLE

        if(restTimer !=null) {
            restTimer!!.cancel()
            restProgress =0
        }
        loadGif(false)
        setRestProgressBar()
    }

    private fun setExerciseProgressBar() {
        progress_bar_exercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000, 1000){
            override fun onTick(p0: Long) {
                exerciseProgress ++
                progress_bar_exercise.progress = 30 - exerciseProgress
                tv_timer_exercise.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                println("Time is up!")
                if(exerciseIndex < 5) {
                    Toast.makeText(this@ExerciseActivity, "Great job. Time to take a rest!", Toast.LENGTH_SHORT).show()
                    setupRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "YOU MADE IT!", Toast.LENGTH_SHORT).show()
                }

            }
        }.start()
    }

    private fun  setupExerciseView () {
        ll_exercise_view.visibility = View.VISIBLE
        ll_rest_view.visibility = View.GONE

        if(exerciseTimer !=null) {
            exerciseTimer!!.cancel()
            exerciseProgress =0
        }
        loadGif(true)

        var exercise = workoutParams["exerciseNames"]?.get(exerciseIndex)

        if(exercise == null) exercise = "Default Text"
        tv_get_exercise.text = exercise


        setExerciseProgressBar()
    }

    // Check if the GIF is animated or not:
    private fun loadGif(isAnimationActive: Boolean){
        if (isAnimationActive){
            var gif = workoutParams["exerciseGifs"]?.get(exerciseIndex)
            if(gif == null){
                gif = "https://media.giphy.com/media/3ornjHnlfyrQclXGZq/giphy.gif"
            }
            Glide.with(this)
                .asGif()  // Load as animated GIF
                .load(gif)  // Call your GIF here (url, raw, etc.)
                .into(exerciseGif)
        } else {
            Glide.with(this)
                .asGif()
                .load("https://media.giphy.com/media/KHVbpEYoTraB736p3d/giphy.gif")  // Call your GIF here (url, raw, etc.)
                .into(restGif)
        }
    }


}