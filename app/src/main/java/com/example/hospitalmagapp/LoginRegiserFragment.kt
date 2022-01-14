package com.example.hospitalmagapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.hospitalmagapp.databinding.FragmentLoginRegiserBinding


class LoginRegiserFragment : Fragment() {
   private lateinit var binding: FragmentLoginRegiserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginRegiserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.LoginRegiserpatientLoginBtnId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginRegiserFragment_to_patientLogin)
        }
        binding.loginRegisterDoctorBtnId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginRegiserFragment_to_doctorLoginFragment)
        }
        binding.loginRegisterAdminBtnId.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginRegiserFragment_to_adminFragment)
        }
    }

}