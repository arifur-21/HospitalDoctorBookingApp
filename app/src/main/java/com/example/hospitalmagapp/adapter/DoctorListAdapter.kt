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
import com.example.hospitalmagapp.model.DoctorModel
import com.squareup.picasso.Picasso

class DoctorListAdapter(var context: Context, var doctorList: ArrayList<DoctorModel>):
    RecyclerView.Adapter<DoctorListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.doctor_laist_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = doctorList[position]
        holder.address.text = data.address
        holder.email.text = data.email
        holder.name.text = data.name
        Picasso.get().load(data.image).placeholder(R.drawable.doctor).into(holder.image)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()

            bundle.putString("address",data.address)
            bundle.putString("email", data.email)
            bundle.putString("experience", data.experience.toString())
            bundle.putString("qualification", data.qualification)
            bundle.putString("name", data.name)
            bundle.putString("fees", data.fees.toString())
            bundle.putString("image", data.image)
            bundle.putString("speciality", data.speciality)

            Navigation.findNavController(it).navigate(R.id.action_doctorListFragment_to_doctorProfileFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val image = itemView.findViewById<ImageView>(R.id.doctorListLayoutImageId)
        val name = itemView.findViewById<TextView>(R.id.doctorListLayoutDoctorNameId)
        val email = itemView.findViewById<TextView>(R.id.doctorListLayoutEmailId)
        val address = itemView.findViewById<TextView>(R.id.doctorListLayoutAddressId)

    }
}