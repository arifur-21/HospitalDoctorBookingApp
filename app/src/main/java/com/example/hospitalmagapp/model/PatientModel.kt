package com.example.hospitalmagapp.model

data class PatientModel(
        val name: String = "",
        val phone: Int? = null,
        val address: String = "",
        val age: Int? = null,
        val gender: String = "",
        val bloodGroup: String = "",
        val appointTime: String = ""

) {
}