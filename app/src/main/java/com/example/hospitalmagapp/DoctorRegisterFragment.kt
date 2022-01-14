package com.example.hospitalmagapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.hospitalmagapp.databinding.FragmentDoctorRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class DoctorRegisterFragment : Fragment() {


    private lateinit var binding: FragmentDoctorRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDoctorRegisterBinding.inflate(layoutInflater)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.doctorRegisterSingUpTvId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_doctorRegisterFragment_to_doctorLoginFragment)
        }

        binding.doctorRegisterBtnId.setOnClickListener {
            register()
            Navigation.findNavController(view).navigate(R.id.action_doctorRegisterFragment_to_doctorLoginFragment)
        }
    }

    private fun register() {
        val fname = binding.doctorRegisterFirestNameId.text.toString()
        val lname = binding.doctorRegisterLastNameId.text.toString()
        val email = binding.doctorRegisterEmailId.text.toString()
        val pass = binding.doctorRegisterPasswordId.text.toString()
        val phone = binding.doctorRegisterPhoneId.text.toString()

        if (fname.isEmpty()){binding.doctorRegisterFirestNameId.setError("Enter your First Name")}
        else if (lname.isEmpty()){binding.doctorRegisterLastNameId.setError("Enter your Last Name")}
        else if (lname.isEmpty()){binding.doctorRegisterLastNameId.setError("Enter your Last Name")}
        else if (email.isEmpty()){binding.doctorRegisterEmailId.setError("Enter your Email")}
        else if (pass.isEmpty()){binding.doctorRegisterPasswordId.setError("Enter your Password")}
        else if (phone.isEmpty()){binding.doctorRegisterPhoneId.setError("Enter your Phone Number")}
        else{

            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener { task->
                if (!task.isSuccessful){
                    Toast.makeText(requireContext(), "Error ${task.exception.toString()}", Toast.LENGTH_SHORT).show()
                }else{
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                    val userInfo: MutableMap<String, Any> = HashMap()

                    userInfo["First Name"] = fname
                    userInfo["Last Name"] = lname
                    userInfo["email"] = email
                    userInfo["Phone"] = phone

                    db.collection("UserInfo").add(userInfo).addOnCompleteListener { task->
                        if (task.isSuccessful){

                            Toast.makeText(requireContext(), "Register Successfull", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }

        }
    }
}