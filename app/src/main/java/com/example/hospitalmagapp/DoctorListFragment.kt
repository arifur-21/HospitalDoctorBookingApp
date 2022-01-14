package com.example.hospitalmagapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmagapp.adapter.DoctorListAdapter
import com.example.hospitalmagapp.model.DoctorModel
import com.google.firebase.firestore.*


class DoctorListFragment : Fragment() {

    private lateinit var specialist: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var doctorListAdapter: DoctorListAdapter
    private lateinit var doctorList: ArrayList<DoctorModel>
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_doctor_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        specialist = view.findViewById(R.id.doctorListSpecialityId)
        recyclerView = view.findViewById(R.id.doctorListFragmentRecyViewId)

        val bundle = arguments
         val doctorType =   bundle!!.getString("type")
         Log.e("type", doctorType.toString())
        specialist.text = doctorType

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        doctorList = arrayListOf()
        doctorListAdapter = DoctorListAdapter(requireContext(), doctorList)
        recyclerView.adapter = doctorListAdapter

        if (doctorType != null && doctorType.contentEquals("dentist")){
            db.collection("doctorProfile").whereEqualTo("type","dentist").addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null){
                        Log.e("error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            doctorList.add(dc.document.toObject(DoctorModel::class.java))
                        }
                    }
                    doctorListAdapter.notifyDataSetChanged()
                }

            })
        }

        if (doctorType != null && doctorType.contentEquals("cardiology")){
            db.collection("doctorProfile").whereEqualTo("type","cardiology").addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null){
                        Log.e("error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            doctorList.add(dc.document.toObject(DoctorModel::class.java))
                        }
                    }
                    doctorListAdapter.notifyDataSetChanged()
                }

            })
        }

    }
}