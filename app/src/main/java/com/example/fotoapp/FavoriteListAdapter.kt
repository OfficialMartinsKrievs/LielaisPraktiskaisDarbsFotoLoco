package com.example.fotoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorite_list.view.*

class FavoriteListAdapter(private val data: ArrayList<FavoriteModel>) :
    RecyclerView.Adapter<FavoriteListAdapter.FavoriteHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = FavoriteHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.favorite_list, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(data[position])
    }


    class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val locationName = view.favorite_location_name
        private val cameraName = view.favorite_camera_name
        private val filterName = view.favorite_filter_name

        fun bind(location: FavoriteModel) {
            locationName.text = location.location
            cameraName.text = location.camera
            filterName.text = location.filter
        }

    }
}