package com.example.hospitalmagapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmagapp.adapter.SpecialitiesAdapter
import com.example.hospitalmagapp.model.SpecialitiesModel
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage


class SpecialitiesFragment : Fragment() {

    private lateinit var specialitiesAdapter: SpecialitiesAdapter
    private lateinit var specialistList: ArrayList<SpecialitiesModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        db = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_specialities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.specialitiesRecycelViewId)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        specialistList = arrayListOf()
        specialitiesAdapter = SpecialitiesAdapter(requireContext(), specialistList)
        recyclerView.adapter = specialitiesAdapter

        db.collection("specialities").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("error", error.message.toString())
                    return
                }

                for (dc: DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        specialistList.add(dc.document.toObject(SpecialitiesModel::class.java))
                    }
                }
                specialitiesAdapter.notifyDataSetChanged()
            }

        })

    }

}