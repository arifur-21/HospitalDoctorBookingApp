package com.example.hospitalmagapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hospitalmagapp.model.ServiceModel
import com.example.hospitalmagapp.repository.Repository

class ServiceViewModel(val repository: Repository): ViewModel() {

    fun featchService(): MutableLiveData<List<ServiceModel>>{
        return  repository.getService()
    }
}