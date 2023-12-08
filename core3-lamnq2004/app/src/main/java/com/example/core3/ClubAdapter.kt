package com.example.core3

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ClubAdapter : ListAdapter<Club, ClubAdapter.ViewHolder>(ClubDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_component, parent, false) as View

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val club = getItem(position)
        holder.bind(club)
    }

//  ViewHolder class
    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){

        private val name : TextView = v.findViewById(R.id.name)
        private val location : TextView = v.findViewById(R.id.location)
        private val date : TextView = v.findViewById(R.id.date)
        private val type : ImageView = v.findViewById(R.id.icon)

        fun bind(club : Club){
//          Set text view for club item
            setTextView(club)

//          Set club icon
            setClubIcon(club)

//          Change text color
            updateLocationTextColor(club)

            Log.d("ClubAdapter", "Binding club with id: ${club.id}")
        }

        private fun updateLocationTextColor(index : Club){
            if(index.location == "Online"){
                location.setTextColor(Color.parseColor("#5D9C59"))
            } else{
                location.setTextColor(Color.parseColor("#FF000000"))
            }
        }

        private fun setClubIcon(index: Club){
            when (index.type){
                "Tech" -> type.setImageResource(R.drawable.microchip_solid)
                "Cultural" -> type.setImageResource(R.drawable.plane_solid)
                "Politics" -> type.setImageResource(R.drawable.landmark_solid)
                "Sport" -> type.setImageResource(R.drawable.volleyball_solid)
            }
        }

        private fun setTextView(index: Club){
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")
            val formattedDateTime = index.dateTime.format(formatter)

            name.text = index.clubName
            location.text = index.location
            date.text = formattedDateTime
        }
    }

    class ClubDiffCallback : DiffUtil.ItemCallback<Club>() {
        override fun areItemsTheSame(oldItem: Club, newItem: Club): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Club, newItem: Club): Boolean {
            return oldItem == newItem
        }
    }
}