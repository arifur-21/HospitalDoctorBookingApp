package com.example.hospitalmagapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmagapp.R
import com.example.hospitalmagapp.model.ServiceModel
import com.squareup.picasso.Picasso

class ServiceAdapter(var context: Context, var serviceList: ArrayList<ServiceModel>): RecyclerView.Adapter<ServiceAdapter.VeiwHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeiwHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.service_layout, parent, false)
        return VeiwHolder(view)
    }

    override fun onBindViewHolder(holder: VeiwHolder, position: Int) {

        val data = serviceList[position]
        holder.title.text = data.title
        Picasso.get().load(data.image).placeholder(R.drawable.sliderimg1).into(holder.image)

    }

    override fun getItemCount(): Int {
       return serviceList.size
    }

    inner class VeiwHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.showServiceTitleId)
        val image = itemView.findViewById<ImageView>(R.id.showServiceImgId)
    }
}