package com.example.fotoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cameras.*


class CamerasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cameras)

//back poga augsaa
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "New Camera"

//poga
        camera_save_button.setOnClickListener {

            if (camera_name_text.text.toString().trim().isNotEmpty()) {
                val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                val oldSet: Set<String> =
                    prefs?.getStringSet("cameras_list", HashSet()) as Set<String>

                val newSet: MutableSet<String> = HashSet()
                newSet.addAll(oldSet)
                newSet.add(camera_name_text.text.toString())

                Log.i("Cameras", newSet.toString())
                prefs.edit().putStringSet("cameras_list", newSet).apply()
            }
            //pasaka ka uzvedisies aktivitate (kad jauna, veca notirita)
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }

    }

    //override, lai mestu atpakal uz main ekranu
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
