package com.example.hospitalmagapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hospitalmagapp.databinding.FragmentPatientLoginBinding
import com.google.firebase.auth.FirebaseAuth


class PatientLogin : Fragment() {
   private lateinit var binding: FragmentPatientLoginBinding
   private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.patientLoginSingInTvId.setOnClickListener{

            Navigation.findNavController(it).navigate(R.id.action_patientLogin_to_patientRegisterFragment)
        }
        binding.patientLoginBtnId.setOnClickListener {view ->
            login(view)
        }


    }

    private fun login(view: View) {

        val email = binding.patientLoginEmailId.text.toString()
        val pass = binding.patientLoginPasswordId.text.toString()

        if (email.isEmpty()){binding.patientLoginEmailId.setError("Enter your Email")}
        else if (pass.isEmpty()){binding.patientLoginPasswordId.setError("Enter your Password")}
        else{
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener { task->
                if (!task.isSuccessful){

                    Log.e("log", "error${task.exception.toString()}")
                }
                else{
                    Navigation.findNavController(view).navigate(R.id.action_patientLogin_to_homeFragment)
                }
            }
        }

    }

}