package com.internshala.workshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.internshala.database.helpers.DBHelper
import com.internshala.database.models.Workshop
import com.internshala.utils.getCurrentUserID
import com.internshala.workshop.R

/**
 * This class is an adapter for recycler view responsible for displaying
 * enrolled [Workshop] list in 'ProfileFrag' fragment
 *
 * @property context Context
 * @property enrolledList MutableList<Workshop>
 * @constructor
 */
class ProfileEnrolledRecyclerAdapter(
    private val context: Context,
    private val enrolledList: MutableList<Workshop>):
    RecyclerView.Adapter<ProfileEnrolledRecyclerAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var titleText: TextView = itemView.findViewById(R.id.enrolled_workshop_card_title)
            var agendaText: TextView = itemView.findViewById(R.id.enrolled_workshop_card_agenda)
            var durationText: TextView = itemView.findViewById(R.id.enrolled_workshop_card_duration_text)
            var organizerText: TextView = itemView.findViewById(R.id.enrolled_workshop_card_organizer)
            var rollOutBtn: Button = itemView.findViewById(R.id.enrolled_workshop_card_roll_out_btn)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
            .from(parent.context)
            .inflate(R.layout.enrolled_workshop_card,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleText.text = enrolledList[position].title
        holder.organizerText.text = enrolledList[position].organizer
        holder.agendaText.text = enrolledList[position].agenda
        val durationInDays = "${enrolledList[position].durationInDays} days"
        holder.durationText.text = durationInDays
        holder.rollOutBtn.setOnClickListener {
            val myDBHelper  = DBHelper(context)
            val result = myDBHelper.deleteEnrollment(getCurrentUserID(context),
                enrolledList[position].id?:-1)
            if(result){
                enrolledList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, enrolledList.count())
            }else Toast.makeText(context, "Unable to rollout: db error", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return enrolledList.count()
    }
}