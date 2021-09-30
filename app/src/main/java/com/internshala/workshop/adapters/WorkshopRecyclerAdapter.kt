package com.internshala.workshop.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.internshala.auth.activities.Authentication
import com.internshala.database.helpers.DBHelper
import com.internshala.database.models.Workshop
import com.internshala.utils.getCurrentUserID
import com.internshala.utils.isValidUser
import com.internshala.utils.loginUser
import com.internshala.workshop.R

class WorkshopRecyclerAdapter(
    private val context: Context,
    private val workshopList: MutableList<Workshop>,
    private val enrolledList: MutableList<Workshop>
):
    RecyclerView.Adapter<WorkshopRecyclerAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.workshop_card,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleText.text = workshopList[position].title
        holder.organizerText.text = workshopList[position].organizer
        holder.agendaText.text = workshopList[position].agenda
        val durationInDays = "${workshopList[position].durationInDays} days"
        holder.durationText.text = durationInDays
        if(enrolledList.any{ it.id == workshopList[position].id }){
            holder.applyBtn.isEnabled = false
            holder.applyBtn.text = "Enrolled"
        }else{
            holder.applyBtn.isEnabled = true
            holder.applyBtn.text = "Apply"
            holder.applyBtn.setOnClickListener{
                if(isValidUser(context)){
                    val myDBHelper = DBHelper(context)
                    val result = myDBHelper.enrollToWorkshop(getCurrentUserID(context),
                        workshopList[position].id?:-1)
                    if(result) {
                        holder.applyBtn.isEnabled = false
                        holder.applyBtn.text = "Enrolled"
                        Toast.makeText(context, "Enrolled successfully", Toast.LENGTH_SHORT).show()
                    }else Toast.makeText(context, "Unable to enroll: DB error", Toast.LENGTH_SHORT).show()
                }else{
                    context.startActivity(Intent(context, Authentication::class.java))
                }
            }
        }
    }


    override fun getItemCount(): Int {
       return workshopList.count()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var titleText: TextView = itemView.findViewById(R.id.workshop_card_title)
        var agendaText: TextView = itemView.findViewById(R.id.workshop_card_agenda)
        var durationText: TextView = itemView.findViewById(R.id.workshop_card_duration_text)
        var organizerText: TextView = itemView.findViewById(R.id.workshop_card_organizer)
        var applyBtn: Button= itemView.findViewById(R.id.workshop_card_apply_btn)
    }
}