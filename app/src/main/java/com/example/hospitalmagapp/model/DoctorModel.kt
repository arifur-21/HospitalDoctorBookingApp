package com.example.hospitalmagapp.model

import java.io.Serializable

data class DoctorModel(
    val image: String = "",
    val name: String = "",
    val speciality: String = "",
    val address: String = "",
    val experience: Int? = 0,
    val qualification: String = "",
    val  email : String = "",
    val fees: Long? = 0,
    val type : String = ""
):Serializable {
}