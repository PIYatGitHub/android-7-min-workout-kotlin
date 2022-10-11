package com.piy.sevenminutesworkout

import java.lang.Error
import kotlin.random.Random

class WorkoutGenerator {

    companion object {
        private val availableExercises = listOf<String>(
            "Jumping jacks",
            "Squats",
            "Push-ups",
            "Oblique raises",
            "Lateral movements",
            "Lunges",
            "Triceps extensions",
            "High knees",
            "Jumping twist",
            "Hip dips",
            "Oblique knees",
            "Chair hip dips",
            "Candles",
            "Front tilt balancing",
            "Bicycle",
            "V cross punch",
            "Leg raises",
            "Jumping squats",
            "Classic dips",
            "Wall stand",
            "Forward standing lunge",
            "Hook combo",
            "Calf raises",
            "Burpees"
        )
        private val exercisesGifs = listOf<String>(
            "https://media.giphy.com/media/QXg8kI7Mu4slyiO0Md/giphy.gif",
            "https://media.giphy.com/media/1qfKN8Dt0CRdCRxz9q/giphy.gif",
            "https://media.giphy.com/media/ZA68nmuNOXBecacY4Z/giphy.gif",
            "https://media.giphy.com/media/kVTQnTOl7T0djki2D4/giphy.gif",
            "https://media.giphy.com/media/Jmal4FUrHggfITICnv/giphy.gif",
            "https://media.giphy.com/media/kih6UKSrTQslyRv7x2/giphy.gif",
            "https://media.giphy.com/media/kI4FVPzGiyXXOYt8hw/giphy.gif",
            "https://media.giphy.com/media/62aGqZoUJYtPsl0Hb0/giphy.gif",
            "https://media.giphy.com/media/f74oydVbDJG4BSGZt9/giphy.gif",
            "https://media.giphy.com/media/dXRLYeOuD8JqRhcQoU/giphy.gif",
            "https://media.giphy.com/media/5tmpTAIMt4H89W7H0G/giphy.gif",
            "https://media.giphy.com/media/3mgBYwj3yju1Uqd4R6/giphy.gif",
            "https://media.giphy.com/media/4NkiLPY496NLZaaAz1/giphy.gif",
            "https://media.giphy.com/media/kI9406irKjqLpGuevW/giphy.gif",
            "https://media.giphy.com/media/cM8tzmeTY0SYMz6VGj/giphy.gif",
            "https://media.giphy.com/media/d9Bf1XOelckUh0puL5/giphy.gif",
            "https://media.giphy.com/media/KBaOv5b7NxcAUgXwsW/giphy.gif",
            "https://media.giphy.com/media/vvzFNc1kRSqTzrFLOT/giphy.gif",
            "https://media.giphy.com/media/wKdb2xwADyl3hQZmZh/giphy.gif",
            "https://media.giphy.com/media/WOMf2YUgi1yiUIbbWm/giphy.gif",
            "https://media.giphy.com/media/VInzBwqrtIBF8IfWSJ/giphy.gif",
            "https://media.giphy.com/media/SvpgNG0tZTlFYf8q2p/giphy.gif",
            "https://media.giphy.com/media/2wXXVCek2NfkneGqz9/giphy.gif",
            "https://media.giphy.com/media/1n59T3BEQ0Q8IJHZQx/giphy.gif"
        )

        fun generateNewWorkout(): MutableMap<String, MutableList<String>> {
            val generatedExerciseNames = mutableListOf<String>()
            val generatedExerciseGifs = mutableListOf<String>()
            val availableWorkoutsLen = availableExercises.size;
            val alreadyInSet = mutableMapOf<Int, Boolean>()
            val returnAssets = mutableMapOf<String, MutableList<String>>()
            try {
                println(">>>START GENERATING A WORKOUT")
                while (generatedExerciseGifs.size != 12 && generatedExerciseNames.size != 12) {
                    val nextInRange = Random.nextInt(0, availableWorkoutsLen)
                    if (!alreadyInSet.containsKey(nextInRange)) {
                        println("GOTCHA getting new workout item @index $nextInRange")
                        generatedExerciseNames.add(availableExercises[nextInRange])
                        generatedExerciseGifs.add(exercisesGifs[nextInRange])
                        alreadyInSet[nextInRange] = true
                    }
                }
                println(">>> GENERATING A WORKOUT -- DONE")
                returnAssets["exerciseNames"] = generatedExerciseNames
                returnAssets["exerciseGifs"] = generatedExerciseGifs
                return returnAssets

            } catch (e: Error) {
                println("This has backfired, pal!")
                returnAssets["exerciseNames"] = mutableListOf()
                returnAssets["exerciseGifs"] = mutableListOf()
                return returnAssets
            }

        }
    }
}