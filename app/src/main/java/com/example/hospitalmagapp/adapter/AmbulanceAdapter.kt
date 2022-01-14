package com.example.hospitalmagapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmagapp.R
import com.example.hospitalmagapp.model.AmblunceModel
import com.squareup.picasso.Picasso

class AmbulanceAdapter(var context: Context, var ambulanceList: ArrayList<AmblunceModel>):
    RecyclerView.Adapter<AmbulanceAdapter.viewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ambulance_layout, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = ambulanceList[position]

        holder.address.text = data.address
        holder.hospital.text = data.hospital
        holder.name.text = data.name
        holder.phone.text = data.phone.toString()
        Picasso.get().load(data.image).into(holder.image)

    }

    override fun getItemCount(): Int {
        return ambulanceList.size
    }

    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val image = itemView.findViewById<ImageView>(R.id.driverImageId)
        val name = itemView.findViewById<TextView>(R.id.driverNameId)
        val phone = itemView.findViewById<TextView>(R.id.driverPhoneId)
        val hospital = itemView.findViewById<TextView>(R.id.hospitalNameId)
        val address = itemView.findViewById<TextView>(R.id.hospitalAddressId)
        val call = itemView.findViewById<ImageView>(R.id.driverCallImgId)
    }
}