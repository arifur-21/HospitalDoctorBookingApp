package com.example.hospitalmagapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.hospitalmagapp.databinding.FragmentDoctorLoginBinding
import com.google.firebase.auth.FirebaseAuth


class DoctorLoginFragment : Fragment() {

    private lateinit var binding: FragmentDoctorLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()

        binding.doctorLoginSingInTvId.setOnClickListener{

            Navigation.findNavController(it).navigate(R.id.action_doctorLoginFragment_to_doctorRegisterFragment)
        }
        binding.doctorLoginBtnId.setOnClickListener {view ->
            login(view)
        }
    }

    private fun login(view: View) {

        val email = binding.doctorLoginEmailId.text.toString()
        val pass = binding.doctorLoginPasswordId.text.toString()

        if (email.isEmpty()){binding.doctorLoginEmailId.setError("Enter your Email")}
        else if (pass.isEmpty()){binding.doctorLoginPasswordId.setError("Enter your Password")}
        else{
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener { task->
                if (!task.isSuccessful){
                    Log.e("log", "error${task.exception.toString()}")
                }
                else{

                    Navigation.findNavController(view).navigate(R.id.action_doctorLoginFragment_to_appointedPatientFragment)
                }
            }
        }

    }

}