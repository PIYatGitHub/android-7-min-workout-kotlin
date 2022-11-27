package com.piy.sevenminutesworkout

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_exercise.*
import org.w3c.dom.Text
import java.lang.Error
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseProgress = 0
    private var exerciseIndex = -1
    val workoutParams = WorkoutGenerator.generateNewWorkout()
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null
    private val adapterItems = mutableListOf(
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )

    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Bad data on TTS!")
            } else {
                Log.e("TTS", "TTS init failed!")
            }
        }
    }


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

        tts = TextToSpeech(this, this)
        setupExerciseStatusRecyclerView()
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player != null) {
            player!!.stop()
        }
        super.onDestroy()
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setRestProgressBar() {
        progress_bar_rest.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                progress_bar_rest.progress = 10 - restProgress
                tv_timer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                println("Time is up!")
                Toast.makeText(
                    this@ExerciseActivity,
                    "Now we start the exercise!",
                    Toast.LENGTH_SHORT
                ).show()
                var size: Int = workoutParams["exerciseGifs"]?.size ?: throw Error("bad data!")

                if (exerciseIndex < size - 1) {
                    exerciseIndex++
                    adapterItems[exerciseIndex] = true
                    println("@onFinish Adapter items are $adapterItems")
                    exerciseAdapter!!.notifyDataSetChanged()
                } else {
                    exerciseIndex = 0
                }

                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView() {
        ll_exercise_view.visibility = View.GONE
        ll_rest_view.visibility = View.VISIBLE

        try {
            player = MediaPlayer.create(applicationContext, R.raw.timer)
            player!!.isLooping = false
            player!!.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        loadGif(false)

        if (exerciseIndex <= 10) { //there are still indices left
            val exercise = workoutParams["exerciseNames"]?.get(exerciseIndex + 1)
            if (exercise != null) {
                tv_set_exercise_name.text = exercise
            }
        }

        setRestProgressBar()
    }

    private fun setExerciseProgressBar() {
        progress_bar_exercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                progress_bar_exercise.progress = 30 - exerciseProgress
                tv_timer_exercise.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                println("Time is up!")
                if (exerciseIndex < 12) {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Great job. Time to take a rest!",
                        Toast.LENGTH_SHORT
                    ).show()
                    setupRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "YOU MADE IT!", Toast.LENGTH_SHORT).show()
                    finish() //finish the exercise activity
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }

            }
        }.start()
    }

    private fun setupExerciseView() {
        ll_exercise_view.visibility = View.VISIBLE
        ll_rest_view.visibility = View.GONE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        loadGif(true)

        var exerciseName = workoutParams["exerciseNames"]?.get(exerciseIndex)

        if (exerciseName == null) exerciseName = "Default Text"
        tv_get_exercise.text = exerciseName

        speakOut(exerciseName)
        setExerciseProgressBar()
    }

    // Check if the GIF is animated or not:
    private fun loadGif(isAnimationActive: Boolean) {
        if (isAnimationActive) {
            var gif = workoutParams["exerciseGifs"]?.get(exerciseIndex)
            if (gif == null) {
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

    private fun setupExerciseStatusRecyclerView() {
        rv_exercise_status.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        println("@ setupExerciseStatusRecyclerView Adapter items are $adapterItems")
        exerciseAdapter = ExerciseStatusAdapter(adapterItems, this)
            rv_exercise_status.adapter = exerciseAdapter
    }
}