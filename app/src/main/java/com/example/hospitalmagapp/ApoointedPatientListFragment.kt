package com.example.hospitalmagapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospitalmagapp.adapter.AppointtedListAdapter
import com.example.hospitalmagapp.databinding.FragmentApoointedPatientListBinding
import com.example.hospitalmagapp.model.PatientProfileModel
import com.google.firebase.firestore.*


class ApoointedPatientListFragment : Fragment() {

    private lateinit var binding: FragmentApoointedPatientListBinding
    private lateinit var appointedListAdapter: AppointtedListAdapter
    private lateinit var appointtedList: ArrayList<PatientProfileModel>
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApoointedPatientListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val bundle = arguments
        val type =   bundle!!.getString("type")
        Log.e("type", type.toString())

        binding.appointedPatientListRecycleviewId.layoutManager = LinearLayoutManager(requireContext())
        appointtedList = arrayListOf()
        appointedListAdapter = AppointtedListAdapter(requireContext(), appointtedList)
        binding.appointedPatientListRecycleviewId.adapter = appointedListAdapter

        db = FirebaseFirestore.getInstance()
        if (type != null && type.contentEquals("dentist")){
            db.collection("PatientProfile").whereEqualTo("type", "dentist").addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            appointtedList.add(dc.document.toObject(PatientProfileModel::class.java))
                        }
                    }
                    appointedListAdapter.notifyDataSetChanged()
                }

            })
        }


        if (type != null && type.contentEquals("cardiology")){
            db.collection("PatientProfile").whereEqualTo("type", "cardiology").addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            appointtedList.add(dc.document.toObject(PatientProfileModel::class.java))
                        }
                    }
                    appointedListAdapter.notifyDataSetChanged()
                }

            })
        }

    }


}