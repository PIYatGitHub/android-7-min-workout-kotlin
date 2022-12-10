package com.piy.sevenminutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val IMPERIAL_UNITS_VIEW = "IMPERIAL_UNIT_VIEW"
    var currentlyVisibleView = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        setSupportActionBar(toolbar_bmi)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }
        toolbar_bmi.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateResult.setOnClickListener {
            val isMetric = currentlyVisibleView == METRIC_UNITS_VIEW
            val toast =
                Toast.makeText(this@BMIActivity, "Please enter a valid value.", Toast.LENGTH_LONG)
            var bmi = 0.0f;

            if (isMetric && valudateMetricUnits()) {
                val height: Float = editTextMetricUnitHeight.text.toString().toFloat() / 100
                val weight: Float = editTextMetricUnitWeight.text.toString().toFloat()

                bmi = weight / (height * height)
            }

            if (!isMetric && validateImperialUnits()) {
                val heightInFeet: String = editTextImperialUnitHeightFeet.text.toString()
                val heightInInch: String = editTextImperialUnitHeightInches.text.toString()
                val weightInPounds: Float = editTextImperialUnitWeight.text.toString().toFloat()

                val heightValue = heightInInch.toFloat() + heightInFeet.toFloat() * 12

                bmi = 703 * (weightInPounds/(heightValue*heightValue))
            }

            if (bmi!= 0.0f){
                displayBMIResult(bmi)
            } else {
                toast.show()
            }
        }

        makeVisibleMetricUnitsView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisiblImperialUnitsView()
            }
        }
    }

    private fun displayBMIResult(bmi: Float) {
        var label = ""
        var bmiDescr = "Ooops! You really need healthcare. Eat more!"

        if (bmi.compareTo(15f) <= 0) {
            label = "Very severely underweight"
        }

        if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            label = "Severely underweight"
        }

        if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            label = "Underweight"
        }

        if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            label = "Normal"
            bmiDescr = "Great shape. Workout to keep it that way."
        }

        if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            label = "Overweight"
            bmiDescr = "Ooops! You really need to start these workouts!"
        }

        if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            label = "Obese class I (Moderately Obese)"
            bmiDescr = "Ooops! You really need to start these workouts!"
        }

        if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            label = "Obese class II (Severely Obese)"
            bmiDescr = "Ooops! You really need to start these workouts! So not underestimate this!"
        }

        if (bmi.compareTo(40f) > 0) {
            label = "Obese class III (Morbidly Obese)"
            bmiDescr = "This is not a joke soldier! Get it together or you die..."
        }

        displayBMIResult.visibility = View.VISIBLE

        val bmival = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        tvBMIValue.text = bmival
        tvBMIType.text = label
        tvBMIDescription.text = bmiDescr
    }

    private fun valudateMetricUnits(): Boolean {
        var isValid = true

        if (editTextMetricUnitWeight.text.toString().isEmpty()) isValid = false
        if (editTextMetricUnitHeight.text.toString().isEmpty()) isValid = false

        return isValid
    }

    private fun validateImperialUnits(): Boolean {
        var isValid = true

        if (editTextImperialUnitHeightFeet.text.toString().isEmpty()) isValid = false
        if (editTextImperialUnitHeightInches.text.toString().isEmpty()) isValid = false
        if (editTextImperialUnitWeight.text.toString().isEmpty()) isValid = false

        return isValid
    }

    private fun makeVisibleMetricUnitsView() {
        currentlyVisibleView = METRIC_UNITS_VIEW
        textInputLayoutUnitWeight.visibility = View.VISIBLE
        textInputLayoutUnitHeight.visibility = View.VISIBLE
        textInputLayoutImperialWeight.visibility = View.GONE
        llImperialUnitsHeight.visibility = View.GONE
        displayBMIResult.visibility = View.INVISIBLE

        editTextMetricUnitWeight.text!!.clear()
        editTextMetricUnitHeight.text!!.clear()
    }

    private fun makeVisiblImperialUnitsView() {
        currentlyVisibleView = IMPERIAL_UNITS_VIEW
        textInputLayoutUnitWeight.visibility = View.GONE
        textInputLayoutUnitHeight.visibility = View.GONE
        textInputLayoutImperialWeight.visibility = View.VISIBLE
        llImperialUnitsHeight.visibility = View.VISIBLE
        displayBMIResult.visibility = View.INVISIBLE

        editTextImperialUnitWeight.text!!.clear()
        editTextImperialUnitHeightFeet.text!!.clear()
        editTextImperialUnitHeightInches.text!!.clear()
    }
}