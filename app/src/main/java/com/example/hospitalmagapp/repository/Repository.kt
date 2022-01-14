package com.example.hospitalmagapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.hospitalmagapp.model.ServiceModel
import com.google.firebase.firestore.FirebaseFirestore

class Repository {

    private var db: FirebaseFirestore? = null
    private val COLLECTION_SERVICES = "ourServices"

    init {
        db = FirebaseFirestore.getInstance()
    }

    fun getService(): MutableLiveData<List<ServiceModel>>{
        val serverMutableList: MutableLiveData<List<ServiceModel>> = MutableLiveData()

        db!!.collection(COLLECTION_SERVICES).get().addOnSuccessListener { result->
            val servicList : MutableList<ServiceModel> = ArrayList()

            for (document in result){
                val service : ServiceModel = document.toObject(ServiceModel::class.java)
                servicList.add(service)
            }
            serverMutableList.postValue(servicList)
        }
        return serverMutableList
    }
}