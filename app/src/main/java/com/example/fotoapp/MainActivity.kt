package com.example.fotoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var fRecyclerView: RecyclerView
    private lateinit var fViewAdapter: RecyclerView.Adapter<*>
    private lateinit var fViewManager: RecyclerView.LayoutManager
    private lateinit var cRecyclerView: RecyclerView
    private lateinit var cViewAdapter: RecyclerView.Adapter<*>
    private lateinit var cViewManager: RecyclerView.LayoutManager
    private lateinit var cameras: ArrayList<String>
    private lateinit var favorites: ArrayList<FavoriteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//pogas
        add_camera.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CamerasActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }

        add_favorite.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    FavoriteActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val gson = Gson()
        val prefsFavorites = prefs.getString("favorite_list", null)

        favorites = ArrayList()
        if (prefsFavorites != null) {
            val arrayType = object : TypeToken<ArrayList<FavoriteModel>>() {}.type
            favorites = gson.fromJson(prefsFavorites, arrayType)
        }

//masivs stringiem no sharedprefs
        val prefsCameras: Set<String> = prefs.getStringSet("cameras_list", HashSet()) as Set<String>
        cameras = prefsCameras.toTypedArray().toCollection(ArrayList())

        if (prefsCameras == null) {
            add_favorite.hide()
        } else {
            add_favorite.show()
        }

//lauj katra rindina savu rakstit piesaistam vertibas recycleview layoutam xml
        fViewManager = LinearLayoutManager(this)
        fViewAdapter = FavoriteListAdapter(favorites)

        fRecyclerView = findViewById<RecyclerView>(R.id.favorite_list).apply {
            layoutManager = fViewManager
            adapter = fViewAdapter
        }

        cViewManager = LinearLayoutManager(this)
        cViewAdapter = CameraListAdapter(cameras)

        cRecyclerView = findViewById<RecyclerView>(R.id.camera_list).apply {
            layoutManager = cViewManager
            adapter = cViewAdapter
        }
    }
}
