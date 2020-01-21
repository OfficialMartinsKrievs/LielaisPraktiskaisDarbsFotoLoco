package com.example.fotoapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_favorite.*


class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

//back poga
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "New Location"

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
//uz kameras
        val prefsCameras: Set<String> = prefs.getStringSet("cameras_list", HashSet()) as Set<String>
        val cameras: ArrayList<String> = prefsCameras.toTypedArray().toCollection(ArrayList())
        val camerasMenu = PopupMenu(this, cameras_options)
        for (s in cameras) {
            camerasMenu.menu.add(s)
        }
        cameras_options.setOnClickListener {
            camerasMenu.show()
            camerasMenu.setOnMenuItemClickListener { item ->
                cameras_options.setText(item.title)
                true
            }
        }

//uz filtriem
        filter_options.setOnClickListener {
            PopupMenu(this, filter_options).apply {
                menuInflater.inflate(R.menu.filters, menu)
                setOnMenuItemClickListener { item ->
                    filter_options.setText(item.title)
                    true
                }
                show()
            }
        }

        favorite_save_button.setOnClickListener {
            //pec click dabu no txt field vardu location
            val LocName = favorite_name_text.text.toString()
            val LocCamera = cameras_options.text.toString()
            val LocFilter = filter_options.text.toString()

            if (LocName.isEmpty() || LocCamera.isEmpty() || LocFilter.isEmpty()) {
                return@setOnClickListener
            }

            val prefsFavorites = prefs.getString("favorite_list", null)
            val gson = Gson()
            val favorites = FavoriteModel(LocName, LocCamera, LocFilter)
            if (prefsFavorites == null) {
                val json = gson.toJson(favorites)

                //saglabaa prefs json
                prefs.edit().putString("favorite_list", "[$json]").apply()
            } else {
                val arrayType = object : TypeToken<ArrayList<FavoriteModel>>() {}.type
                val array: ArrayList<FavoriteModel> = gson.fromJson(prefsFavorites, arrayType)
                //pievienojam array klat
                array.add(favorites)
                val jsonArray = gson.toJson(array)
                prefs.edit().putString("favorite_list", jsonArray).apply()
            }

            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == android.R.id.home) {
            onBackPressed()
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
