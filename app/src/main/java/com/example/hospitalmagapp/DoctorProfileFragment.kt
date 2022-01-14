package com.example.hospitalmagapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.hospitalmagapp.databinding.FragmentDoctorProfileBinding
import com.example.hospitalmagapp.model.DoctorModel
import com.squareup.picasso.Picasso

class DoctorProfileFragment : Fragment() {

    private lateinit var binding: FragmentDoctorProfileBinding
    private var doctorModel: DoctorModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentDoctorProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle = arguments
        val address : String? = bundle!!.getString("address")
        val name : String? = bundle.getString("name")
        val email : String? = bundle.getString("email")
        val fees : String? = bundle.getString("fees")
        val image : String? = bundle.getString("image")
        val experience : String? = bundle.getString("experience")
        val qualification : String? = bundle.getString("qualification")
        val speciality: String? = bundle.getString("speciality")


        binding.doctorProfielAddressId.text = address
        binding.doctorProfileEmailId.text = email
        binding.doctorProfileExperienceId.text = experience + " years"
        binding.doctorProfileFeesId.text = fees + " TK"
        binding.doctorProfileQualificationId.text =qualification
        binding.showDoctorProfileNameId.text = name
        binding.doctorProfileSpecialityId.text = speciality
       Picasso.get().load(image).into(binding.showDoctorProfileImgId)

        val bundle1 = Bundle()
        bundle1.putString("fees",fees)
        bundle1.putString("speciality", speciality)

        binding.doctorAppointmentBtnId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_doctorProfileFragment_to_appointmentFragment, bundle1)
        }


    }
}