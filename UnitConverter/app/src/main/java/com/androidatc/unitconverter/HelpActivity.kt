package com.androidatc.unitconverter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HelpActivity : AppCompatActivity() {
    private lateinit var homebutton: Button
    private lateinit var settingsbutton: Button
    private lateinit var recentbutton: Button
    private lateinit var clear_button: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        homebutton = findViewById(R.id.home_button)
        settingsbutton = findViewById(R.id.settings_button)
        recentbutton = findViewById(R.id.recent_button)



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

    }
}