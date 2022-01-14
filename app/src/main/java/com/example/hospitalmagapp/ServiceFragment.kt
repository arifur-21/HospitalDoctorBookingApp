package com.example.hospitalmagapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmagapp.adapter.ServiceAdapter
import com.example.hospitalmagapp.model.ServiceModel
import com.example.hospitalmagapp.viewmodel.ServiceViewModel
import com.google.firebase.firestore.*


class ServiceFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var serviceAdapter: ServiceAdapter
    private lateinit var serviceList : ArrayList<ServiceModel>
    private val serviceViewModel: ServiceViewModel by viewModels()
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        db = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.serviceRecycleViewId)
        recyclerView.layoutManager = LinearLayoutManager(requireContext());
        serviceList = arrayListOf()
        serviceAdapter = ServiceAdapter(requireContext(), serviceList)
        recyclerView.adapter = serviceAdapter



        db.collection("ourServices").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("error", error.message.toString())
                    return
                }

                for (dc: DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        serviceList.add(dc.document.toObject(ServiceModel::class.java))
                    }
                }
                serviceAdapter.notifyDataSetChanged()
            }

        })


    }


}