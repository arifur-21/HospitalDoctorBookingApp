package com.example.hospitalmagapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.hospitalmagapp.databinding.FragmentPatientRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PatientRegisterFragment : Fragment() {

    private lateinit var binding : FragmentPatientRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentPatientRegisterBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.patientRegisterSingUpTvId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_patientRegisterFragment_to_patientLogin)
        }

        binding.patientRegisterBtnId.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val fname = binding.patientRegisterFirestNameId.text.toString()
        val lname = binding.patientRegisterLastNameId.text.toString()
        val email = binding.patientRegisterEmailId.text.toString()
        val pass = binding.patientRegisterPasswordId.text.toString()
        val phone = binding.patientRegisterPhoneId.text.toString()

        if (fname.isEmpty()){binding.patientRegisterFirestNameId.setError("Enter your First Name")}
        else if (lname.isEmpty()){binding.patientRegisterLastNameId.setError("Enter your Last Name")}
        else if (lname.isEmpty()){binding.patientRegisterLastNameId.setError("Enter your Last Name")}
        else if (email.isEmpty()){binding.patientRegisterEmailId.setError("Enter your Email")}
        else if (pass.isEmpty()){binding.patientRegisterPasswordId.setError("Enter your Password")}
        else if (phone.isEmpty()){binding.patientRegisterPhoneId.setError("Enter your Phone Number")}
        else{

           auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener { task->
               if (!task.isSuccessful){
                   Toast.makeText(requireContext(), "Error ${task.exception.toString()}", Toast.LENGTH_SHORT).show()
               }else{
                   val userId = FirebaseAuth.getInstance().currentUser!!.uid
                   val userInfo: MutableMap<String, Any> = HashMap()

                   userInfo.put("First Nmae", fname)
                   userInfo.put("Last Name", lname)
                   userInfo.put("email", email)
                   userInfo.put("Phone", phone)

                   db.collection("UserInfo").add(userInfo).addOnCompleteListener { task->
                       if (task.isSuccessful){

                       }
                   }
               }
           }

        }
    }

}