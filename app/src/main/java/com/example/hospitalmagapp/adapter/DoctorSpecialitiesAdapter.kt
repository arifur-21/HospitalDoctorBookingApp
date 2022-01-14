package com.example.hospitalmagapp.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmagapp.R
import com.example.hospitalmagapp.model.SpecialitiesModel
import com.squareup.picasso.Picasso

class DoctorSpecialitiesAdapter(var context: Context, var specialitiesList: ArrayList<SpecialitiesModel>):
    RecyclerView.Adapter<DoctorSpecialitiesAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.specialities_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data = specialitiesList[position]

        holder.title.text = data.title
        Picasso.get().load(data.image).placeholder(R.drawable.doctor).into(holder.image)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type",data.type)
            Navigation.findNavController(it).navigate(R.id.action_appointedPatientFragment_to_apoointedPatientListFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return  specialitiesList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val image = itemView.findViewById<ImageView>(R.id.specialitiesLayoutImgId)
        val title = itemView.findViewById<TextView>(R.id.specialitiesLayoutTiteId)
    }
}