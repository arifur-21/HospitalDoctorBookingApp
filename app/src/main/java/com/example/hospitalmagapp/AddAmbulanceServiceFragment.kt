package com.example.hospitalmagapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hospitalmagapp.databinding.FragmentAddAmbulanceServiceBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask


class AddAmbulanceServiceFragment : Fragment() {

    private lateinit var binding: FragmentAddAmbulanceServiceBinding
    private var imageUri: Uri? = null
    private var myUrl = ""
    private lateinit var db: FirebaseFirestore
    private var storagePostPicRef: StorageReference?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentAddAmbulanceServiceBinding.inflate(layoutInflater)
        db = FirebaseFirestore.getInstance()
        storagePostPicRef = FirebaseStorage.getInstance().reference.child("Ambulance Driver Picture")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addAmbulanceServiceImgId.setOnClickListener{
            val intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 77)
        }

        binding.addAmbulanceServiceSaveBtnId.setOnClickListener {
            saveDoctorProfile()
        }
    }


    private fun saveDoctorProfile() {

        val name = binding.addAmbulanceServicenameId.text.toString()
        val address = binding.addAmbulanceServiceaddressId.text.toString()
        val phone = binding.addAmbulanceServicePhoneId.text.toString()
        val hospitalName = binding.addAmbulanceServiceHospitalNameId.text.toString()


        if (name.isEmpty()){binding.addAmbulanceServicenameId.setError("Enter your Name")}
        else if (address.isEmpty()){binding.addAmbulanceServiceaddressId.setError("Enter your Address")}
        else if (phone.isEmpty()){binding.addAmbulanceServicePhoneId.setError("Enter your Phone Number")}
        else if (hospitalName.isEmpty()){binding.addAmbulanceServiceHospitalNameId.setError("Enter your Hospital Name")}
        else if (imageUri == null){
            Toast.makeText(requireContext(), "Select your Image", Toast.LENGTH_SHORT).show()
        }
        else{
            /// store image firebaseStorage and get image url
            val fileRef = storagePostPicRef!!.child(System.currentTimeMillis().toString() + ".jpg")
            var uploadTask: StorageTask<*>
            uploadTask = fileRef.putFile(imageUri!!)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw  it
                    }
                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener(OnCompleteListener { task ->

                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    myUrl = downloadUrl.toString()

                    val doctorInfo: HashMap<String, Any> = HashMap()

                    doctorInfo["image"] = myUrl
                    doctorInfo["name"] = name
                    doctorInfo["address"] = address
                    doctorInfo["phone"] = phone.toLong()
                    doctorInfo["hospital"] = hospitalName


                    db.collection("emergency").add(doctorInfo).addOnCompleteListener { task->
                        if (task.isSuccessful){
                            Toast.makeText(requireContext(),"Ambulance Service added successfull", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== 77 && resultCode == Activity.RESULT_OK && data != null ){
            imageUri = data.data
            binding.addAmbulanceServiceImgId.setImageURI(imageUri)

            Toast.makeText(requireContext(),"picture uploaded", Toast.LENGTH_SHORT).show()
        }
    }


}