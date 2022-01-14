package com.example.hospitalmagapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmagapp.adapter.AmbulanceAdapter
import com.example.hospitalmagapp.model.AmblunceModel
import com.google.firebase.firestore.*


class EmergentyFragment : Fragment() {

    private lateinit var ambulanceAdapter: AmbulanceAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var driverList: ArrayList<AmblunceModel>
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_emergenty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.emergencyRecycleViewId)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        driverList = arrayListOf()
        ambulanceAdapter = AmbulanceAdapter(requireContext(), driverList)
        recyclerView.adapter = ambulanceAdapter

        db.collection("emergency").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error != null){
                    Log.e("error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value!!.documentChanges){
                    if (dc.type == DocumentChange.Type.ADDED){
                        driverList.add(dc.document.toObject(AmblunceModel::class.java))
                    }
                }
                ambulanceAdapter.notifyDataSetChanged()
            }

        })

    }

}