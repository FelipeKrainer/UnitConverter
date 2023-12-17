package com.androidatc.unitconverter

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    private lateinit var radioButtonCm: RadioButton
    private lateinit var radioButtonCm2: RadioButton
    private lateinit var radioButtonMm: RadioButton
    private lateinit var radioButtonMm2: RadioButton
    private lateinit var radioButtonKm: RadioButton
    private lateinit var radioButtonKm2: RadioButton
    private lateinit var radioButtonFt: RadioButton
    private lateinit var radioButtonFt2: RadioButton
    private lateinit var radioButtonMi: RadioButton
    private lateinit var radioButtonMi2: RadioButton
    private lateinit var homebutton: Button
    private lateinit var settingsbutton: Button
    private lateinit var recentbutton: Button
    private lateinit var helpbutton: FloatingActionButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var radiogroup1: RadioGroup
    private lateinit var radiogroup2: RadioGroup

    private lateinit var result_text: TextView
    private val sharedPreferencesKey = "unitPreferences"








    //Conversion factors

    //cm to others
    private val conversionFactorCmToMm = 10.0
    private val conversionFactorCmToKm = 0.00001
    private val conversionFactorCmToFt = 0.0328084
    private val conversionFactorCmToMi = 6.2137e-6

    //mm to others
    private val conversionFactorMmToCm = 0.1
    private val conversionFactorMmToKm = 0.000001
    private val conversionFactorMmToFt = 0.00328084
    private val conversionFactorMmToMi = 0.00000062137
    //Km to others
    private val conversionFactorKmToCm = 100000.00
    private val conversionFactorKmTomm = 1000000.00
    private val conversionFactorKmToFt = 3280.84
    private val conversionFactorKmToMi = 0.621371
    //Ft to others
    private val conversionFactorFtToCm = 30.48
    private val conversionFactorFtTomm = 304.8
    private val conversionFactorFtToKm = 0.0003048
    private val conversionFactorFtToMi = 0.000189394
    //Mi to others
    private val conversionFactorMiToCm = 160934.4
    private val conversionFactorMiTomm = 1609344.00
    private val conversionFactorMiToKm = 1.60934
    private val conversionFactorMiToFt = 5280.00




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("ConversionHistory", Context.MODE_PRIVATE)

        val button1 = findViewById<Button>(R.id.save_button)
        homebutton = findViewById(R.id.home_button)
        settingsbutton = findViewById(R.id.settings_button)
        recentbutton = findViewById(R.id.recent_button)
        helpbutton = findViewById(R.id.helpButton)

        radiogroup1 = findViewById(R.id.radioGroup1)
        radiogroup2 = findViewById(R.id.radioGroup2)




        radioButtonCm = findViewById(R.id.centimeter)
        radioButtonCm2 = findViewById(R.id.centimeter2)
        radioButtonMm = findViewById(R.id.millimeter)
        radioButtonMm2 = findViewById(R.id.millimeter2)
        radioButtonKm = findViewById(R.id.kilometer)
        radioButtonKm2 = findViewById(R.id.kilometer2)
        radioButtonFt = findViewById(R.id.feet)
        radioButtonFt2 = findViewById(R.id.feet2)
        radioButtonMi = findViewById(R.id.miles)
        radioButtonMi2 = findViewById(R.id.miles2)
        result_text = findViewById(R.id.result)


        sharedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)


        val savedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        updateRadioGroupFromPreferences(savedPreferences, radiogroup1)
        updateRadioGroupFromPreferences(savedPreferences, radiogroup2)


        homebutton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        settingsbutton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        recentbutton.setOnClickListener {
            val intent = Intent(this, RecentActivity::class.java)
            startActivity(intent)
        }
        helpbutton.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }






        button1.setOnClickListener {
            convertValues()
        }



    }

    private fun convertValues() {
        // Get the value from the EditText widget
        val inputValueText = findViewById<EditText>(R.id.firstunit_entry).text.toString()

        if (inputValueText.isNotEmpty()) {
            val inputValue = inputValueText.toDouble()

            val selectedUnit1 = determineSelectedUnit()
            val selectedUnit2 = determineSelectedUnit2()

            if (selectedUnit1 != null && selectedUnit2 != null) {
                val conversionFactor: Double = when {
                    //Cm to others
                    (selectedUnit1 == radioButtonCm && selectedUnit2 == radioButtonCm2)  -> 1.0
                    (selectedUnit1 == radioButtonCm && selectedUnit2 == radioButtonMm2) -> conversionFactorCmToMm
                    (selectedUnit1 == radioButtonCm && selectedUnit2 == radioButtonKm2) -> conversionFactorCmToKm
                    (selectedUnit1 == radioButtonCm && selectedUnit2 == radioButtonFt2) -> conversionFactorCmToFt
                    (selectedUnit1 == radioButtonCm && selectedUnit2 == radioButtonMi2) -> conversionFactorCmToMi
                    //MM to others
                    (selectedUnit1 == radioButtonMm && selectedUnit2 == radioButtonCm2) -> conversionFactorMmToCm
                    (selectedUnit1 == radioButtonMm && selectedUnit2 == radioButtonMm2) -> 1.0
                    (selectedUnit1 == radioButtonMm && selectedUnit2 == radioButtonKm2) -> conversionFactorMmToKm
                    (selectedUnit1 == radioButtonMm && selectedUnit2 == radioButtonFt2) -> conversionFactorMmToFt
                    (selectedUnit1 == radioButtonMm && selectedUnit2 == radioButtonMi2) -> conversionFactorMmToMi
                    //Km to others
                    (selectedUnit1 == radioButtonKm && selectedUnit2 == radioButtonCm2) -> conversionFactorKmToCm
                    (selectedUnit1 == radioButtonKm && selectedUnit2 == radioButtonKm2) -> 1.0
                    (selectedUnit1 == radioButtonKm && selectedUnit2 == radioButtonMm2) -> conversionFactorKmTomm
                    (selectedUnit1 == radioButtonKm && selectedUnit2 == radioButtonFt2) -> conversionFactorKmToFt
                    (selectedUnit1 == radioButtonKm && selectedUnit2 == radioButtonMi2) -> conversionFactorKmToMi
                    //Ft to others
                    (selectedUnit1 == radioButtonFt && selectedUnit2 == radioButtonCm2) -> conversionFactorFtToCm
                    (selectedUnit1 == radioButtonFt && selectedUnit2 == radioButtonKm2) -> conversionFactorFtToKm
                    (selectedUnit1 == radioButtonFt && selectedUnit2 == radioButtonMm2) -> conversionFactorFtTomm
                    (selectedUnit1 == radioButtonFt && selectedUnit2 == radioButtonFt2) -> 1.0
                    (selectedUnit1 == radioButtonFt && selectedUnit2 == radioButtonMi2) -> conversionFactorFtToMi
                    //Mi to others
                    (selectedUnit1 == radioButtonMi && selectedUnit2 == radioButtonCm2) -> conversionFactorMiToCm
                    (selectedUnit1 == radioButtonMi && selectedUnit2 == radioButtonKm2) -> conversionFactorMiToKm
                    (selectedUnit1 == radioButtonMi && selectedUnit2 == radioButtonMm2) -> conversionFactorMiTomm
                    (selectedUnit1 == radioButtonMi && selectedUnit2 == radioButtonFt2) -> conversionFactorMiToFt
                    (selectedUnit1 == radioButtonMi && selectedUnit2 == radioButtonMi2) -> 1.0

                    (selectedUnit1 == radioButtonCm2 && selectedUnit2 == radioButtonCm) || (selectedUnit1 == radioButtonCm && selectedUnit2 == radioButtonCm2) -> 1.0
                    (selectedUnit1 == radioButtonMm2 && selectedUnit2 == radioButtonKm) || (selectedUnit1 == radioButtonKm && selectedUnit2 == radioButtonMm2) -> 1 / conversionFactorCmToMm
                    (selectedUnit1 == radioButtonKm2 && selectedUnit2 == radioButtonCm) || (selectedUnit1 == radioButtonCm && selectedUnit2 == radioButtonKm2) -> 1 / conversionFactorCmToKm
                    (selectedUnit1 == radioButtonMi2 && selectedUnit2 == radioButtonCm) || (selectedUnit1 == radioButtonCm && selectedUnit2 == radioButtonMi2) -> 1 / conversionFactorCmToMi
                    // Add similar cases for other units

                    else -> 0.0
                }

                val result: Double = inputValue * conversionFactor
                result_text.text = "Result: $result"

                val conversionEntry = ConversionEntry(
                    selectedUnit1.text.toString(),
                    selectedUnit2.text.toString(),
                    result,
                    System.currentTimeMillis()
                )
                storeConversionEntry(conversionEntry)



            } else {
                result_text.text = "Invalid units selected"
            }
        } else {
            result_text.text = "Please enter a number"
        }
    }





    private fun determineSelectedUnit(): RadioButton? {
        return when {
            radioButtonCm.isChecked -> radioButtonCm
            radioButtonMm.isChecked -> radioButtonMm
            radioButtonKm.isChecked -> radioButtonKm
            radioButtonFt.isChecked -> radioButtonFt
            radioButtonMi.isChecked -> radioButtonMi
            else -> null
        }
    }

    private fun determineSelectedUnit2(): RadioButton? {
        return when {
            radioButtonCm2.isChecked -> radioButtonCm2
            radioButtonMm2.isChecked -> radioButtonMm2
            radioButtonKm2.isChecked -> radioButtonKm2
            radioButtonFt2.isChecked -> radioButtonFt2
            radioButtonMi2.isChecked -> radioButtonMi2
            else -> null
        }
    }





    private fun updateRadioGroupFromPreferences(preferences: SharedPreferences, radioGroup: RadioGroup) {
        val selectedRadioButtonId = preferences.getInt(radioGroup.tag.toString(), -1)
        if (selectedRadioButtonId != -1) {
            radioGroup.check(selectedRadioButtonId)
        }
    }

    private fun getConversionHistory(): MutableList<ConversionEntry> {
        val gson = Gson()
        val json = sharedPreferences.getString("conversionHistory", "")
        return if (json.isNullOrEmpty()) {
            mutableListOf()
        } else {
            gson.fromJson(json, object : TypeToken<MutableList<ConversionEntry>>() {}.type)
        }
    }



    private fun storeConversionEntry(entry: ConversionEntry) {
        val gson = Gson()
        val entries: MutableList<ConversionEntry> = getConversionHistory()

        // Remove any existing entry with the same timestamp
        entries.removeAll { it.timestamp == entry.timestamp }

        entries.add(0, entry) // Add to the beginning of the list

        // Limit to the last 10 entries
        if (entries.size > 10) {
            entries.subList(10, entries.size).clear()
        }

        val json = gson.toJson(entries)

        sharedPreferences.edit().putString("conversionHistory", json).apply()
    }


















}

