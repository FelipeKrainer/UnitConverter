package com.androidatc.unitconverter

import RecentConversionsAdapter
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecentActivity : AppCompatActivity() {

    private lateinit var homebutton: Button
    private lateinit var settingsbutton: Button
    private lateinit var recentbutton: Button
    private lateinit var helpbutton: FloatingActionButton
    private lateinit var clear_button: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent)

        recyclerView = findViewById(R.id.recyclerView)
        homebutton = findViewById(R.id.home_button)
        settingsbutton = findViewById(R.id.settings_button)
        recentbutton = findViewById(R.id.recent_button)
        helpbutton = findViewById(R.id.helpButton)
        clear_button = findViewById(R.id.clear_button)


        // Initialize sharedPreferences
        sharedPreferences = getSharedPreferences("unitPreferences", Context.MODE_PRIVATE)

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

        clear_button.setOnClickListener {
            if (clearConversionHistory()) {
                // Refresh the RecyclerView or update UI as needed
                // For example, you can reload the recent conversions after clearing
                val emptyList = listOf<ConversionEntry>()
                setupRecyclerView(emptyList)
            } else {
                // Handle failure to clear conversion history
            }
        }


        val recentConversions = getRecentConversions()
        Log.d("RecentActivity", "Recent Conversions: $recentConversions") // Log the recent conversions
        setupRecyclerView(recentConversions)
    }

    private fun getConversionHistory(): List<ConversionEntry> {
        val gson = Gson()
        val json = sharedPreferences.getString("conversionHistory", "")
        Log.d("RecentActivity", "Conversion History JSON: $json")
        return if (json.isNullOrEmpty()) {
            mutableListOf()
        } else {
            gson.fromJson(json, object : TypeToken<List<ConversionEntry>>() {}.type)
        }
    }


    private fun getRecentConversions(): List<ConversionEntry> {
        val conversionHistory = getConversionHistory()
        return conversionHistory.take(10) // Take the last 10 entries
    }


    private fun setupRecyclerView(conversions: List<ConversionEntry>) {
        val adapter = RecentConversionsAdapter(conversions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    private fun clearConversionHistory(): Boolean {
        return try {
            sharedPreferences.edit().remove("conversionHistory").apply()
            true // Successfully cleared
        } catch (e: Exception) {
            // Handle exception (e.g., log or display an error message)
            false // Failed to clear
        }
    }
}
