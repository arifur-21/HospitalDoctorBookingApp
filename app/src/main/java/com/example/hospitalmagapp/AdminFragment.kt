package com.example.hospitalmagapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.hospitalmagapp.databinding.FragmentAdminBinding


class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAdminBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adminAddOurServicesBtnId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_adminFragment_to_addOurServicesFragment)
        }

        binding.adminAddSpecialityServicesBtnId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_adminFragment_to_addSpecialityList2)
        }

        binding.adminAddDoctorProfileId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_adminFragment_to_addDoctorProfileFragment)
        }

        binding.adminAddAmbulanceServiceId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_adminFragment_to_addAmbulanceServiceFragment)
        }
    }


}