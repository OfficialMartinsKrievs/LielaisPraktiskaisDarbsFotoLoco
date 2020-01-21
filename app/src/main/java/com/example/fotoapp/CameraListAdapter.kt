package com.example.fotoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.camera_list.view.*

class CameraListAdapter(private val data: ArrayList<String>) :
    RecyclerView.Adapter<CameraListAdapter.CameraHolder>() {

    //piesaistam layout, kad jauns dats ievadits
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CameraHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.camera_list, parent, false)
    )

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: CameraHolder, position: Int) {
        holder.bind(data[position])
    }

    class CameraHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.camera_list_name

        fun bind(title: String) {
            name.text = title
        }

    }
}