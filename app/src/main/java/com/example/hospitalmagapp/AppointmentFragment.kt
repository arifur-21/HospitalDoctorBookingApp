package com.example.hospitalmagapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.hospitalmagapp.databinding.FragmentAppointmentBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AppointmentFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentBinding

    private lateinit var formattedDate: String
    private var gender: String ="Male"
    private var fees: Long? =0
    private var speciality: String = ""
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentAppointmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            db = FirebaseFirestore.getInstance()

        val bundle = arguments
        fees = bundle!!.getString("fees")?.toLong()
        speciality =  bundle!!.getString("speciality").toString()
        Log.e("main", fees.toString())


        val bundle1 = Bundle()
        bundle1.putString("fees", fees.toString())
        binding.appointmentPatientProfileSaveBtnId.setOnClickListener {
            saveProfile()
            Navigation.findNavController(it).navigate(R.id.action_appointmentFragment_to_purchaseFragment, bundle1)
        }

        binding.calanderId.setOnClickListener{
            calender()
        }

        binding.patientRadioGroupId.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.patientFemaleRadioBtnId){
                gender = "Femail"
            }
            if (checkedId == R.id.patientMaleRadioBtnId){
                gender = "Male"
            }
        }

        binding.bloodGroupSpinnerId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    private fun saveProfile() {
        val name = binding.patientNameId.text.toString()
        val phone = binding.patientPhoneId.text.toString()
        val age = binding.patientAgeId.text.toString()
        val address = binding.patientAddressId.text.toString()
        val bloodGroup = binding.bloodGroupSpinnerId.selectedItem.toString()
        val appoinTime = binding.appontmentTimepinnerId.selectedItem.toString()

        if (name.isEmpty()){binding.patientNameId.setError("Enter your Name") }
        else if(age.isEmpty()){binding.patientAgeId.setError("Enter your age")}
        else if(phone.isEmpty()){binding.patientAgeId.setError("Enter your phone number")}
        else if(address.isEmpty()){binding.patientAddressId.setError("Enter your address")}
        else if (bloodGroup == "Select your blood group "){
            Toast.makeText(context, "Select Blood Group", Toast.LENGTH_SHORT).show()
        }
        else if (appoinTime == "Select your Appointment Time "){
            Toast.makeText(context, "Select your appointment Time", Toast.LENGTH_SHORT).show()
        }else{

            val patintInfo: HashMap<String, Any> = HashMap()
            patintInfo["name"] = name
            patintInfo["age"] = age
            patintInfo["phone"] = phone
            patintInfo["address"] = address
            patintInfo["bloodGroup"] = bloodGroup
            patintInfo["appointmentTime"] = appoinTime
            patintInfo["gender"] = gender
            patintInfo["fees"] = fees.toString()
            patintInfo["appointmentDate"] =formattedDate
            patintInfo["type"] = speciality
             db.collection("PatientProfile").add(patintInfo).addOnCompleteListener { task->
                 if (task.isSuccessful){
                     Toast.makeText(requireContext(),"Patient Profile added successfull", Toast.LENGTH_SHORT).show()
                 }
             }
        }

    }

    private fun calender() {
        val calendar = Calendar.getInstance()

        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }
    var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->
        val calendar = Calendar.getInstance()
        calendar[i, i1] = i2
        val sfd = SimpleDateFormat("dd/MM/yyyy")
        formattedDate = sfd.format(calendar.time)
        binding.dateshowTvId.text = formattedDate
    }



}