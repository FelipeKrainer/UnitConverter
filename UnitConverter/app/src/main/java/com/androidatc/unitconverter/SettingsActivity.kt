package com.androidatc.unitconverter

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.RadioGroup
import android.content.SharedPreferences
import android.widget.RadioButton
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SettingsActivity : AppCompatActivity() {

    private lateinit var homebutton: Button
    private lateinit var settingsbutton: Button
    private lateinit var recentbutton: Button
    private lateinit var helpbutton: FloatingActionButton
    private lateinit var radiogroup1: RadioGroup
    private lateinit var radiogroup2: RadioGroup
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

    private val sharedPreferencesKey = "unitPreferences"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        homebutton = findViewById(R.id.home_button)
        settingsbutton = findViewById(R.id.settings_button)
        recentbutton = findViewById(R.id.recent_button)
        helpbutton = findViewById(R.id.helpButton)

        radiogroup1 = findViewById(R.id.radiogroup1)
        radiogroup2 = findViewById(R.id.radiogroup2)


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

        radioButtonCm.isChecked = true
        radioButtonCm2.isChecked = true







        val savedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        updateRadioGroupFromPreferences(savedPreferences, radiogroup1)
        updateRadioGroupFromPreferences(savedPreferences, radiogroup2)

        val saveButton: Button = findViewById(R.id.save_button)
        saveButton.setOnClickListener{
            savePreferences(radiogroup1)
            savePreferences(radiogroup2)
        }


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

    }

    private fun updateRadioGroupFromPreferences(preferences: SharedPreferences, radioGroup: RadioGroup) {
        val selectedRadioButtonId = preferences.getInt(radioGroup.tag.toString(), -1)
        if (selectedRadioButtonId != -1) {
            radioGroup.check(selectedRadioButtonId)
        }
    }


    private fun savePreferences(radioGroup: RadioGroup) {
        val editor = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE).edit()

        // Get the selected radio button ID
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId

        // Save the selected radio button ID to SharedPreferences
        if (selectedRadioButtonId != -1) {
            editor.putInt(radioGroup.tag.toString(), selectedRadioButtonId)
            editor.apply()
        } else {
            radioButtonCm.isChecked = true
            radioButtonCm2.isChecked = true
            // Handle the case when no radio button is selected
            // You may want to provide a default value or show an error message
            // For example, you can set a default value like this:
            // editor.putInt(radioGroup.tag.toString(), defaultRadioButtonId)
            // editor.apply()
        }
    }

}
