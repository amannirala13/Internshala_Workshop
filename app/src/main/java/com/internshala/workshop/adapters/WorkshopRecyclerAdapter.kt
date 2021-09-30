package com.internshala.workshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.internshala.database.models.Workshop
import com.internshala.workshop.R

class WorkshopRecyclerAdapter(private val context: Context, private val workshopList: MutableList<Workshop>):
    RecyclerView.Adapter<WorkshopRecyclerAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.workshop_card, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleText.text = workshopList[position].title
        holder.organizerText.text = workshopList[position].organizer
        holder.agendaText.text = workshopList[position].agenda
        val durationInDays = "${workshopList[position].durationInDays} days"
        holder.durationText.text = durationInDays
        holder.applyBtn.setOnClickListener{
            Toast.makeText(context, "Apply", Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int {
       return workshopList.count()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var titleText: TextView = itemView.findViewById(R.id.workshop_card_title)
        var agendaText: TextView = itemView.findViewById(R.id.workshop_card_agenda)
        var durationText: TextView = itemView.findViewById(R.id.workshop_card_duration_text)
        var organizerText: TextView = itemView.findViewById(R.id.workshop_card_organizer)
        var applyBtn: Button= itemView.findViewById(R.id.workshop_card_apply_btn)
    }
}