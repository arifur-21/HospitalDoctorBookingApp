package com.example.hospitalmagapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmagapp.R
import com.example.hospitalmagapp.model.PatientProfileModel

class AppointtedListAdapter(val context: Context, var appointtedList: ArrayList<PatientProfileModel>): RecyclerView.Adapter<AppointtedListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.appointted_patient_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val list = appointtedList[position]

        holder.name.text = list.name
        holder.date.text = list.appointmentDate
        holder.time.text = list.appointmentTime
    }

    override fun getItemCount(): Int {
     return appointtedList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val name = itemView.findViewById<TextView>(R.id.appointted_patientLayoutNameId)
        val date = itemView.findViewById<TextView>(R.id.appointtedPatientLayoutDateId)
        val time = itemView.findViewById<TextView>(R.id.appointted_patientLayoutTimeId)
    }
}